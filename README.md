# StringSpliter
split stirng as input size

Demo:
  	StringSpliter spliter = new StringSpliter();
	DemoEntity entity = new DemoEntity();
	entity.setId("111");
	entity.setName("name111");
	entity.setValue("1234567890111");
	List<String> list = spliter.split(entity.getId(), entity.getValue(), 5);	
	for (String val : list) {	
	    System.out.println(val);	
	}
	Map<String, String> map = spliter.merge(list);	
	String value = map.get(entity.getId());	
	System.out.println(value);	
	
//output:<br>
  111-1/3:12345<br><br>
  111-2/3:67890<br>
  111-3/3:111<br>
  1234567890111<br>
