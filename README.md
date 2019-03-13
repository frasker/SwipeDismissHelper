# SwipeDismissHelper
支持Android侧滑退出，类似iOS效果
##设置方式
在Android5.0下需要配置android:windowIsTranslucent = true才可以生效
```
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SwipeDismissHelper(this);
    }
}
##依赖
项目放在jitpack上

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	dependencies {
	        implementation 'com.github.frasker:SwipeDismissHelper:v1.0.0-alpha'
	}
  
```
