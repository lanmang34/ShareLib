##集成文档
#1.在主工程assets文件夹中添加ShareSDK.xml
#2.在project的gradle.properties中添加如下配置(复制下面全部文件):

projectCompileSdkVersion=23
projectTargetSdkVersion=23
projectBuildToolsVersion=23.0.3
projectMinSdkVersion=15

#sharelib库配置 start
compileQQ=true
compileQZone=true
compileWechat=true
compileWechatMoments=false
compileSinaWeibo=false
compileShortMessage=false
qqAppId=1105687500
#sharelib库配置 end
