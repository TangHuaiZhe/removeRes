package util

import org.gradle.internal.impldep.org.eclipse.jgit.annotations.NonNull
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * author: tang
 * created on: 2018/3/20 16:05
 * description:线程池管理类
 */
class ThreadPoolManager private constructor() {
  private val cpuCount = Runtime.getRuntime().availableProcessors()
  private val executor: ThreadPoolExecutor = Executors.newFixedThreadPool(2 * cpuCount + 1) as ThreadPoolExecutor

  val isOver: Boolean
    get() = executor.queue.isEmpty() && executor.activeCount == 0

  /**
   * 执行任务
   */
  fun execute(runnable: Runnable) {
    executor.execute(runnable)
  }

  override fun toString(): String {
    return "ThreadPoolManager(executor=$executor)"
  }

  companion object {

    @Volatile
    private var mInstance: ThreadPoolManager? = null

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
