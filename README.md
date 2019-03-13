# SwipeDismissHelper
支持Android侧滑退出，类似iOS效果
## 设置方式
在setContentView后设置
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
```
## 自己处理侧滑逻辑
```
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SwipeDismissHelper(this, new SwipeDismissLayout.OnSwipeProgressChangedListener() {
            @Override
            public void onSwipeProgressChanged(SwipeDismissLayout layout, float alpha, float translate) {
                //TODO
            }

            @Override
            public void onSwipeCancelled(SwipeDismissLayout layout) {
		//TODO
            }
        });
    }
}
```
## 依赖
项目放在jitpack上

```
allprojects {
   repositories {
	...
	maven { url 'https://jitpack.io' }
   }
}
dependencies {
        implementation 'com.github.frasker:SwipeDismissHelper:v1.0.3-alpha'
}
```
