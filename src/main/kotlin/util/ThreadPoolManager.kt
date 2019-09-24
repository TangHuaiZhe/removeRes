package util

import org.gradle.internal.impldep.org.eclipse.jgit.annotations.NonNull
import java.util.concurrent.Callable
import java.util.concurrent.Future
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * author: tang
 * created on: 2018/3/20 16:05
 * description:线程池管理类
 */
class ThreadPoolManager private constructor() {
  private val cpuCount = Runtime.getRuntime().availableProcessors()
  private val corePoolSize = 60
  private val maximumPoolSize = 100
  private val keepAliveTime: Long = 30//存活时间
  private val sPoolWorkQueue = LinkedBlockingQueue<Runnable>(10000)
  private val executor: ThreadPoolExecutor

  private val sThreadFactory = object : ThreadFactory {
    private val mCount = AtomicInteger(1)
    override fun newThread(@NonNull r: Runnable): Thread {
      return Thread(r, "ThreadPoolManager #" + mCount.getAndIncrement())
    }
  }

  /**
   * 判断线程池是否已关闭
   */
  val isShutDown: Boolean
    get() = executor.isShutdown

  /**
   * 关闭线程池后判断所有任务是否都已完成
   */
  val isTerminated: Boolean
    get() = executor.isTerminated

  val isOver: Boolean
    get() = executor.queue.isEmpty()

  init {

    executor = ThreadPoolExecutor(
      corePoolSize,
      maximumPoolSize,
      keepAliveTime,
      TimeUnit.SECONDS,
      sPoolWorkQueue,
      sThreadFactory,
      ThreadPoolExecutor.AbortPolicy()
    )
    executor.allowCoreThreadTimeOut(true)
  }

  /**
   * 执行任务
   */
  fun execute(runnable: Runnable) {
    executor.execute(runnable)
  }

  /**
   * 从线程池中移除任务
   */
  fun remove(runnable: Runnable) {
    executor.remove(runnable)
  }

  fun shutDown() {
    executor.shutdown()
  }

  /**
   * 提交一个Callable任务用于执行
   *
   * @param task 任务
   * @param <T>  泛型
   * @return 表示任务等待完成的Future, 该Future的`get`方法在成功完成时将会返回该任务的结果。
  </T> */
  fun <T> submit(task: Callable<T>): Future<T> {
    return executor.submit(task)
  }

  /**
   * 提交一个Runnable任务用于执行
   *
   * @param task   任务
   * @param result 返回的结果
   * @param <T>    泛型
   * @return 表示任务等待完成的Future, 该Future的`get`方法在成功完成时将会返回该任务的结果。
  </T> */
  fun <T> submit(task: Runnable, result: T): Future<T> {
    return executor.submit(task, result)
  }

  /**
   * 提交一个Runnable任务用于执行
   *
   * @param task 任务
   * @return 表示任务等待完成的Future, 该Future的`get`方法在成功完成时将会返回null结果。
   */
  fun submit(task: Runnable): Future<*> {
    return executor.submit(task)
  }

  override fun toString(): String {
    return "ThreadPoolManager(executor=$executor)"
  }

  companion object {

    @Volatile private var mInstance: ThreadPoolManager? = null

    val instance: ThreadPoolManager
      get() {
        if (mInstance == null) {
          synchronized(ThreadPoolManager::class.java) {
            if (mInstance == null) {
              mInstance = ThreadPoolManager()
            }
          }
        }
        return mInstance!!
      }
  }
}
