import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

fun main() {
    val executor = Executors.newScheduledThreadPool(5)
    var counter = 0

    val lock = ReentrantReadWriteLock()

    val updateCounter = Runnable {
        lock.write {
            counter += 1
        }
    }

    val printCounter = Runnable {
        lock.read {
            println(counter)
        }
    }

    executor.scheduleAtFixedRate(printCounter, 0, 1000, TimeUnit.MILLISECONDS)
    repeat(4){
        executor.scheduleAtFixedRate(updateCounter, 0, 10, TimeUnit.MILLISECONDS)
    }

}