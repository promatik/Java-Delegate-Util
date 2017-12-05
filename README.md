# Java-Delegate-Util
Easy way to add timer events to java

## Usage examples

Run every 10 seconds starting in 10 seconds
```java
Delegate.run(() -> runEveryTenSeconds(), 10, 10, TimeUnit.SECONDS);
```

>```
> 15:16:29> 10s
> 15:16:39> 10s
> 15:16:49> 10s

Run once, in 5 seconds
```java
Delegate.run(() -> myFunction(), 5, TimeUnit.SECONDS);
```

>```
> 15:17:44> 5s
> 

Inline code
```java
Delegate.run(() -> Utils.log(String.valueOf(i++) + "s"), 0, 1, TimeUnit.SECONDS);
```

>```
> 15:18:38> 0s
> 15:18:39> 1s
> 15:18:40> 2s
> 15:18:41> 3s

Stop delegate

```java
Delegate d = Delegate.run(() -> myFunction(), 1, TimeUnit.SECONDS);
...
d.cancel();
```
