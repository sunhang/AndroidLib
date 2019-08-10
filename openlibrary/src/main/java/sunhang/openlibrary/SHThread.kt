package sunhang.openlibrary

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

private val fileExecutor = ThreadPoolExecutor(1, 4, 60L, TimeUnit.SECONDS, SynchronousQueue<Runnable>())

private val dbExecutor = Executors.newSingleThreadExecutor()

/** 主线程 **/
fun runOnMain(task: () -> Unit) = runOnSpecialScheduler(AndroidSchedulers.mainThread(), task)

/** 主线程 **/
fun <T> runOnMain(task: () -> T?, callback: (T?) -> Unit) = runOnSpecialScheduler(AndroidSchedulers.mainThread(), task, callback)

/** 文件相关操作，比如移动、复制等 **/
fun runOnFile(task: () -> Unit) = runOnSpecialScheduler(Schedulers.from(fileExecutor), task)

/** 文件相关操作，比如移动、复制等 **/
fun <T> runOnFile(task: () -> T, callback: (T?) -> Unit) = runOnSpecialScheduler(Schedulers.from(fileExecutor), task, callback)

/** 数据库相关操作 **/
fun runOnDB(task: () -> Unit) = runOnSpecialScheduler(Schedulers.from(dbExecutor), task)

/** 数据库相关操作 **/
fun <T> runOnDB(task: () -> T, callback: (T?) -> Unit) = runOnSpecialScheduler(Schedulers.from(dbExecutor), task, callback)

/** 计算密集型相关操作 **/
fun runOnComputation(task: () -> Unit) = runOnSpecialScheduler(Schedulers.computation(), task)

/** 计算密集型相关操作 **/
fun <T> runOnComputation(task: () -> T, callback: (T?) -> Unit) = runOnSpecialScheduler(Schedulers.computation(), task, callback)

/** 网络io相关操作 **/
fun runOnIO(task: () -> Unit) = runOnSpecialScheduler(Schedulers.io(), task)

/** 网络io相关操作 **/
fun <T> runOnIO(task: () -> T, callback: (T?) -> Unit) = runOnSpecialScheduler(Schedulers.io(), task, callback)

private data class Optional<M>(val value : M?)

private fun runOnSpecialScheduler(scheduler: Scheduler, task: () -> Unit): Disposable {
    return Completable.fromCallable(task)
        .subscribeOn(scheduler)
        .subscribe({}, handleThrowable)
}

private fun <T> runOnSpecialScheduler(scheduler: Scheduler, task: () -> T?, callback: (T?) -> Unit): Disposable {
    /**使用[Optional]的原因是，rxjava不允许返回null了, 否则抛出异常**/
    return Single.fromCallable { return@fromCallable Optional<T?>(task.invoke()) }
        .subscribeOn(scheduler)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            callback.invoke(it.value)
        }, handleThrowable)
}


private val handleThrowable = { it: Throwable ->
    if (BuildConfig.DEBUG) {
        throw it
    } else {
        it.printStackTrace()
    }
}


