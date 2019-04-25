
# Fadecandy RESTful Web Service
Start, Stop, Configure [scanlime/fadecandy/](https://github.com/scanlime/fadecandy/tree/master/server/src) Server Remotely.

## Usecases 

### Get State
`GET /fc`
Looking up current state of the driver.
```json
{
  "value": {
    "isRunning": true,
    "connectedDevices": [
      {
        "type": "Fadecandy ",
        "serial": "HXOTGOEMJZDKCCDP",
        "version": "1.07"
      }
    ]
  },
  "isSuccess": true
}
```

An example of failed request
```json
{
  "isSuccess": false,
  "errorMessage": "An exception was thrown.",
  "stackTrace": "dev.simonas.fadecandyrest.controllers.Fadecandy$getState$1.invoke(Fadecandy.kt:71) , dev.simonas.fadecandyrest.controllers.Fadecandy$getState$1.invoke(Fadecandy.kt:13) , dev.simonas.fadecandyrest.UtilsKt.tryToResult(Utils.kt:9) , dev.simonas.fadecandyrest.controllers.Fadecandy.getState(Fadecandy.kt:70) , dev.simonas.fadecandyrest.services.FadecandyServiceKt$installFadecandyService$1.invokeSuspend(FadecandyService.kt:15) , dev.simonas.fadecandyrest.services.FadecandyServiceKt$installFadecandyService$1.invoke(FadecandyService.kt) , io.ktor.locations.LocationKt$handle$1.invokeSuspend(Location.kt:207) , io.ktor.locations.LocationKt$handle$1.invoke(Location.kt) , io.ktor.util.pipeline.SuspendFunctionGun.loop(PipelineContext.kt:278) , io.ktor.util.pipeline.SuspendFunctionGun.access$loop(PipelineContext.kt:63) , io.ktor.util.pipeline.SuspendFunctionGun.proceed(PipelineContext.kt:137) , io.ktor.util.pipeline.SuspendFunctionGun.execute(PipelineContext.kt:157) , io.ktor.util.pipeline.Pipeline.execute(Pipeline.kt:23) , io.ktor.routing.Routing.executeResult(Routing.kt:148) , io.ktor.routing.Routing.interceptor(Routing.kt:29) , io.ktor.routing.Routing$Feature$install$1.invokeSuspend(Routing.kt:93) , io.ktor.routing.Routing$Feature$install$1.invoke(Routing.kt) , io.ktor.util.pipeline.SuspendFunctionGun.loop(PipelineContext.kt:278) , io.ktor.util.pipeline.SuspendFunctionGun.access$loop(PipelineContext.kt:63) , io.ktor.util.pipeline.SuspendFunctionGun.proceed(PipelineContext.kt:137) , io.ktor.features.ContentNegotiation$Feature$install$1.invokeSuspend(ContentNegotiation.kt:63) , io.ktor.features.ContentNegotiation$Feature$install$1.invoke(ContentNegotiation.kt) , io.ktor.util.pipeline.SuspendFunctionGun.loop(PipelineContext.kt:278) , io.ktor.util.pipeline.SuspendFunctionGun.access$loop(PipelineContext.kt:63) , io.ktor.util.pipeline.SuspendFunctionGun.proceed(PipelineContext.kt:137) , io.ktor.util.pipeline.SuspendFunctionGun.execute(PipelineContext.kt:157) , io.ktor.util.pipeline.Pipeline.execute(Pipeline.kt:23) , io.ktor.server.engine.DefaultEnginePipelineKt$defaultEnginePipeline$2.invokeSuspend(DefaultEnginePipeline.kt:106) , io.ktor.server.engine.DefaultEnginePipelineKt$defaultEnginePipeline$2.invoke(DefaultEnginePipeline.kt) , io.ktor.util.pipeline.SuspendFunctionGun.loop(PipelineContext.kt:278) , io.ktor.util.pipeline.SuspendFunctionGun.access$loop(PipelineContext.kt:63) , io.ktor.util.pipeline.SuspendFunctionGun.proceed(PipelineContext.kt:137) , io.ktor.util.pipeline.SuspendFunctionGun.execute(PipelineContext.kt:157) , io.ktor.util.pipeline.Pipeline.execute(Pipeline.kt:23) , io.ktor.server.netty.NettyApplicationCallHandler$handleRequest$1.invokeSuspend(NettyApplicationCallHandler.kt:31) , io.ktor.server.netty.NettyApplicationCallHandler$handleRequest$1.invoke(NettyApplicationCallHandler.kt) , kotlinx.coroutines.intrinsics.UndispatchedKt.startCoroutineUndispatched(Undispatched.kt:55) , kotlinx.coroutines.CoroutineStart.invoke(CoroutineStart.kt:111) , kotlinx.coroutines.AbstractCoroutine.start(AbstractCoroutine.kt:154) , kotlinx.coroutines.BuildersKt__Builders_commonKt.launch(Builders.common.kt:54) , kotlinx.coroutines.BuildersKt.launch(Unknown Source) , io.ktor.server.netty.NettyApplicationCallHandler.handleRequest(NettyApplicationCallHandler.kt:22) , io.ktor.server.netty.NettyApplicationCallHandler.channelRead(NettyApplicationCallHandler.kt:16) , io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) , io.netty.channel.AbstractChannelHandlerContext.access$600(AbstractChannelHandlerContext.java:38) , io.netty.channel.AbstractChannelHandlerContext$7.run(AbstractChannelHandlerContext.java:353) , io.netty.util.concurrent.AbstractEventExecutor.safeExecute(AbstractEventExecutor.java:163) , io.netty.util.concurrent.SingleThreadEventExecutor.runAllTasks(SingleThreadEventExecutor.java:404) , io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:463) , io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:884) , io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30) , java.lang.Thread.run(Thread.java:745) "
}
```

### Start
`GET /fc/start`
Start the Fadecandy LED driver server.
```json
{
  "value": { },
  "isSuccess": true
}
```

### Stop
`GET /fc/stop`
Stop the Fadecandy server.
```json
{
  "value": { },
  "isSuccess": true
}
```

### Restart
`GET /fc/restart`
Restart the Fadecandy server.
```json
{
  "value": {},
  "isSuccess": true
}
```

### Get Config
`GET /fc/config`
Get Fadecandy servers configuration.
```json
{
  "value": {
    "listen": {
      "ip": "127.0.0.1",
      "port": 7890
    },
    "verbose": true,
    "color": {
      "gamma": 2.5,
      "whitepoint": [
        1.0,
        1.0,
        1.0
      ]
    },
    "devices": [
      {
        "type": "fadecandy",
        "dither": false,
        "interpolate": true,
        "map": [
          [
            0,
            0,
            0,
            512
          ]
        ]
      }
    ]
  },
  "isSuccess": true
}
```

### Set Config
`POST /fc/config`
Modify Fadecandy servers configuration and restart the server to apply changes.

Fadecandy configuration JSON specification can be found [here](https://github.com/scanlime/fadecandy/blob/master/doc/fc_server_config.md).

Apart from `listen` and `relay` properties. Those two are transformed from tuple type to simple object. Since not all languages support Tuple have native support for true Tuples(e.g Kotlin has "Tuple.kt" which actually is a class with two first and second properties).

```json
{
  "value": {
    "listen": {
      "ip": "127.0.0.1",
      "port": 7890
    },
    "verbose": true,
    "color": {
      "gamma": 2.5,
      "whitepoint": [
        1.0,
        1.0,
        1.0
      ]
    },
    "devices": [
      {
        "type": "fadecandy",
        "dither": false,
        "interpolate": true,
        "map": [
          [
            0,
            0,
            0,
            512
          ]
        ]
      }
    ]
  },
  "isSuccess": true
}
```

## Much Thanks For
This wouldn't exist without you [github.com/scanlime/fadecandy](https://github.com/scanlime/fadecandy).

This pretty nice framework [github.com/ktorio/ktor](https://github.com/ktorio/ktor)

Thanks my uni for being pain in the ass [ktu.edu/](https://ktu.edu/)

👋
