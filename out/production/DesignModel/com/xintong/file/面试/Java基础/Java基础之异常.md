# Java基础之异常

##### Java的异常类型

Throwable

- Error
  - AWTErrot
  - VirtualMachineError
    - StackOverflowError
    - OutOfMemoryError

- Exception
  - RuntimeExcrption 非检查型异常
    - NullPointException
    - IllegalArgumentException
    - IndexOutOfBoundException
    - IOException
    - SQLException
  - 检查型异常
    - FileNotFountException
    - ClassNotFoundException

##### **异常如何设计？**

在项目里写一个ServerException继承RuntimeException，再根据不同业务创建不同的Exception继承这个ServerException，在定义一些这类业务出现的常见错误代码



​						