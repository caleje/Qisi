package util

object ConsoleUtils {

  implicit class Paginate[T](items: Seq[T]) {
    def paginate(linesPerPage: Int = 10): Unit = {
      val iter = items.toIterator
      Iterator.continually(scala.io.StdIn.readLine("Press enter"))
        .takeWhile(_.nonEmpty)
        .foreach(println)
      println("Done!!!")
    }
  }
}