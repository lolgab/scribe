package scribe.modify

import scribe.{LogRecord, Priority}

class LogBooster(booster: Double => Double, override val priority: Priority) extends LogModifier {
  override def id: String = "LogBooster"

  override def apply[M](record: LogRecord[M]): Option[LogRecord[M]] = Some(record.boost(booster))
}

object LogBooster {
  def multiply(multiplier: Double, priority: Priority = Priority.Normal): LogBooster = new LogBooster(_ * multiplier, priority)
  def add(value: Double, priority: Priority = Priority.Normal): LogBooster = new LogBooster(_ + value, priority)
  def subtract(value: Double, priority: Priority = Priority.Normal): LogBooster = new LogBooster(_ - value, priority)
}