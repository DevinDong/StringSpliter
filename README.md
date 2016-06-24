# StringSpliter
split stirng as input size

Demo:
  	<code>StringSpliter spliter = new StringSpliter();</code>
	<code>DemoEntity entity = new DemoEntity();</code>
	<code>entity.setId("111");</code>
	<code>entity.setName("name111");</code>
	<code>entity.setValue("1234567890111");</code>
//splite list <br>
<code>
	List<String> list = spliter.split(entity.getId(), entity.getValue(), 5);</code>	
	<code>for (String val : list) {</code>	
	    <code>System.out.println(val);</code>	
	<code>}
//output:<br>
  111-1/3:12345<br><br>
  111-2/3:67890<br>
  111-3/3:111<br>
//merge list<br>
	<code>Map<String, String> map = spliter.merge(list);</code>	
	<code>String value = map.get(entity.getId());</code>	
	<code>System.out.println(value);</code>	
//output
  1234567890111<br>
