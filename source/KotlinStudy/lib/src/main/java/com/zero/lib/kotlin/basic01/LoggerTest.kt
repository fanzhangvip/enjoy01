package com.zero.lib.kotlin.basic01

import sun.net.www.protocol.http.logging.HttpLogFormatter
import java.util.logging.*

fun main() {
//    1. 创建Logger
//    2. 创建Handler,为handler指定Formmater, 然后将Handler添加到logger中去。
//    3. 设定Level级别
    val logger = Logger.getLogger("Zero")
    val consoleHandler = ConsoleHandler()
    consoleHandler.formatter = HttpLogFormatter()
    val memoryHandler = MemoryHandler(consoleHandler,10, Level.ALL)
    logger.addHandler(memoryHandler)
    logger.useParentHandlers = false
    val r1 = LogRecord(Level.INFO,"this info msg")
    val r2 = LogRecord(Level.INFO,"this info msg2")
    logger.log(r1)
    logger.log(r2)
    logger.log(Level.INFO,"logger test")



}