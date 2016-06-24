# StringSpliter
split stirng as input size

Demo:
  StringSpliter spliter = new StringSpliter();
	DemoEntity entity = new DemoEntity();

	entity.setId("111");
	entity.setName("name111");
	entity.setValue("1234567890111");
  //splite list
	List<String> list = spliter.split(entity.getId(), entity.getValue(), 5);
	for (String val : list) {
	    System.out.println(val);
	}
  //merge list
	Map<String, String> map = spliter.merge(list);
	String value = map.get(entity.getId());
	System.out.println(value);
Demo output:
  111-1/3:12345
  111-2/3:67890
  111-3/3:111
  1234567890111
