# walle-cli

### 根据walle官方源码制作，主要是做了打包处理，增加了部分日志输出

[walle-cli-all.jar](walle-cli-all.jar)

### [源码版本](https://github.com/Meituan-Dianping/walle/commit/5a9e399c146678e4fbee06d0877b3bce34bafdc0)
 ```
Commits on Nov 6, 2019
5a9e399c146678e4fbee06d0877b3bce34bafdc0
 ```

[walle源码(美团官方)](https://github.com/Meituan-Dianping/walle)

有兴趣的看看我的其他项目： [https://github.com/itgowo](https://github.com/itgowo)


### 解决问题都是从制造问题开始
## 解决项目流程
${}标识变量，需要手动赋值，$()是用来找文件的，是linux命令
1. 编译apk ./gradlew assembleRelease
2. 删除旧文件创建新目录(任意)
    ```
    rm -rf tool/channels
    mkdir -p tool/channels
    ```
3. 签名(签名工具任意，不懂百度，还不懂谷歌)
    ```
    $ANDROID_HOME/build-tools/29.0.2/apksigner sign --ks tool/sign --ks-key-alias ${alias}  --ks-pass pass:${pass}  --key-pass pass:${pass2} --out tool/channels/app.apk $(ls app/build/outputs/apk/release/*.apk)
    ```
4. 360加固(命令行模式，方便自动化)
    ``` 
   #首次使用必须登录
    java -jar tool/jiagu/jiagu.jar -login ${username} ${password}
   #导入签名文件
   java -jar jiagu/jiagu.jar -importsign tool/sign ${pass} ${alias} ${pass2}
   #配置服务，请看360官网说明选择
   java -jar jiagu/jiagu.jar  -config -update -crashlog -x86
   #上传、加固、下载和自动签名
   java -jar jiagu/jiagu.jar -jiagu tool/channels/app.apk tool/channels -autosign
    ```
5. walle打包
    ``` 
    java -jar walle/walle-cli-all.jar batch2 -f ${channelfile}  $(ls tool/channels/app_*_jiagu_sign.apk) tool/channels
    ```
 