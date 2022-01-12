# Fragment

ktx기준으로 아래와 같이 프래그먼트를 작성할 수 있습니다.

```
class HomeFragment : Fragment(R.layout.home_fragment)
```

그리고 이전과 달리 onCreateView를 오버라이딩 하지 않습니다.

이전코드

```
class HomeFragment : Fragment() {
  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?, savedInstanceState: Bundle?): View{
      return inflater.inflate(R.layout.home_fragment, container, false)
  }
}
```

최근스타일은 아래와 같이 작성할 수 있습니다.

```
class HomeFragment : Fragment(R.layout.home_fragment){
  private lateinit var binding : HomeFragmentBinding // View Binding
  
  override fun onViewCreated(view : View, savedInstanceState: Bundle?){
    binding = HomeFragmentBinding.bind(view)
  }
}
```

액티비티에 Fragment를 붙이려면 xml에 FragmentContainerView를 선언하고, 

android:name에 전체 패키지를 포함한 프래그먼트를 입력하면 선언할 수 있습니다.

ex) android : name = "com.example.fragmentpractice.ui.home.HomeFragment"

코드를 통해 추가하는 방법도 가능합니다

fragment-ktx를 기준으로 아래와 같이 onCreate내에 작성하면 됩니다.
```
supportFragmentManager.commit{
  add<HomeFragment>(R.id.fragment_container_view)
}
```

프래그먼트를 액티비티에 추가하면서 데이터를 넘기려면 bundle형태로 인자를 넘길 수 있습니다.
```
add<HomeFragment>(R.id.fragment_container_view, bundle)
```

만약 프래그먼트에서 이 값을 받으려면 아래와 같이 사용할 수 있습니다.
```
override fun onViewCreated(view : View, savedInstanceState: Bundle?){
    val somInt = arguments.getInt("some_int") // getInt(Key)
  }
```

## FragmentManager

- 프래그먼트 추가, 삭제, 스택관리 등 프래그먼트에 관리에 대한 모든것을 담당합니다.

- getFragmentManager(X) getSupportFragmentManager(O)

- 프래그먼트 내부에서 Fragmentmanager를 가져올 때, getParentFragmentManager와 getChildFragmentManager로나뉩니다.

- 프래그먼트의 인스턴스를 가져올 때, supportFragmentManager.findFragmentById(R.id.fragment_container_view)와 같이 사용할 수 있습니다.

- 또는 xml 내에 tag를선언하고 findFragmentByTag도 사용 가능합니다.





