工程介绍：

	下载地址：
		https://pan.baidu.com/s/1_mnGjtyoqEZl30pWWeDxRQ 密码：qbod
	
	config.proprities:配置文件
			
			homeUrl=http://s405ftp.jios.org:9001/indexcs/simple.jsp?loginErr=0
			网站的地址不用修改
			
			
			
			uploadUrl=http://s405ftp.jios.org:9001/assignment/projectList.jsp?proNum=1&assignID=13
			上传的地址，默认是java的上传地址（可根据自己的需求进行修改）
			
			
			
			pwd=123456
			学生的密码，根据自己设置的修改
			
			
			
			dataDir=D:\\documents\\data
			存放学生的源码文件的地方，注意：文件必须是压缩包的形式，压缩包的名称为学生的用户名如：17056.zip或者U201517056.zip都可以
			
	
	
	StartUp:读入需要进行上传的所有文件的路径并且执行操作
	
	Env:基本的环境，如Properties对象，WebClient对象等
	
	Upload：核心功能
			主要的思路是：通过"homeUrl"进行登录，拿到cookie，然后进入"uploadUrl" 设置表单中上传文件控件的value为文件的路径，点击提交上传

	do.txt:记录上传成功的文件
	err.txt:记录上传发生异常的文件
			
不足：
	当用户名出现异常时会中断操作、性能不足

改进的地方：
	增加容错性、使用多线程提升性能、能够多次启动（发生异常程序会停止）