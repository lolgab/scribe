package spec

import java.nio.file.{Files, Path, Paths}

import org.scalatest.{Matchers, WordSpec}
import scribe.Logger
import scribe.format.Formatter
import scribe.writer.FileWriter
import scribe.writer.action._

import scala.io.Source

class FileLoggingSpec extends WordSpec with Matchers {
  private var fileLogger: Logger = Logger.empty.orphan()
  lazy val logFile: Path = Paths.get("logs/test.log")
  lazy val writer: FileWriter = FileWriter().nio.path(_ => logFile)

  "File Logging" should {
    "configure logging to a temporary file" in {
      if (Files.exists(logFile)) {
        Files.delete(logFile)
      }
      fileLogger = fileLogger.withHandler(formatter = Formatter.simple, writer = writer)
    }
    "log to the file" in {
      fileLogger.info("Testing File Logger")
    }
    "verify the file was logged to" in {
      Files.exists(logFile) should be(true)
      val source = Source.fromFile(logFile.toFile)
      try {
        source.mkString.trim should equal("Testing File Logger")
      } finally {
        source.close()
        Files.delete(logFile)
      }
    }
    // TODO: replace Time.function
    // TODO: proper testing of various file logging features
    "close and release the file handle" in {
      writer.dispose()
    }
  }
}