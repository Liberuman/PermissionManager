[![](https://jitpack.io/v/Liberuman/PermissionManager.svg)](https://jitpack.io/#Liberuman/PermissionManager)
![image](https://img.shields.io/badge/build-passing-brightgreen.svg)
[![image](https://img.shields.io/packagist/l/doctrine/orm.svg)](https://github.com/Liberuman/PermissionManager/blob/master/LICENSE)

从Android6.0开始，Android采用了动态权限机制——在使用时进行申请。但是申请的过程相对比较繁琐，为了简化权限申请的过程，这里采用AOP的思想对申请权限的过程进行了简单的封装，使用时只需要一个注解即可。


#### 添加依赖

在项目的build.gradle中添加：

    dependencies {
        ...
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.5'
    }

    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io'
        }
      }
    }
在APP/Library模块下的build.gradle中引用aspect插件并添加依赖：

    apply plugin: 'android-aspectjx'

    dependencies {
        implementation 'com.github.Liberuman:PermissionManager:0.31'
    }


#### 使用

在Android组件(如Application, Activity, Service, ContentProvider, Fragment, View, Dialog)的类中直接使用CheckPermission注解即可：

    @CheckPermission(permissions = {Manifest.permission.CAMERA}, permissionDesc = "没有权限无法使用相机", settingDesc = "快去设置中开启权限")
    public void openCamera() {
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

    @CheckPermission(permissions = {Manifest.permission.CAMERA})
    public void openCamera2() {
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }

>说明：permissionDesc是权限询问状态时被用户拒绝时的Toast提示，settingDesc是用户直接拒绝后弹出对话框指导用户去设置打开权限的提示。


在非Android组件类中申请权限时需要传入Context类型的参数或实现OnContextListener接口，如：

###### 方法一：

    @CheckPermission(permissions = {Manifest.permission.CAMERA}, permissionDesc = "没有权限无法使用相机", settingDesc = "快去设置中开启权限")
    public void openCamera() {
        startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE));
    }
   
###### 方法二：
 
    public class Example implements OnContextListener {

        private Context context;

        public Example(Context context) {
            this.context = context;
        }

        @CheckPermission(permissions = {Manifest.permission.ACCESS_FINE_LOCATION}, permissionDesc = "没有权限无法定位", settingDesc = "快去设置中开启定位权限")
        public void startLocation(Context context) {
            Toast.makeText(context, "开始定位", Toast.LENGTH_SHORT).show();
        }

        @Override
        public Context getContext() {
            return context;
        }
	}

#### License

    Copyright (c) 2018 Freeman
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
