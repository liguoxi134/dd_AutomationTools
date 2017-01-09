
require.config({
	  baseUrl:"./js",
	  paths:{
		  jquery:"framework/jquery-3.1.1",
		  jqueryValidate:"framework/jquery.validate",
		  knockout:"framework/knockout-3.4.0",
		  Global:"site/base/Global",
		  blade:"site/base/blade",
		  bladeEx:"site/base/bladeEx",
	  }
	});
require(["jquery","jqueryValidate","knockout","Global","blade","bladeEx"],function(){});