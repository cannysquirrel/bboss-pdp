var DesktopMenus = function() {
	var gotomenu = function(menuid,thiselement,e){
		window.location.href = "index.page?menupath_menuid="+menuid;
	}
    var gotoworkspace = function(divid,url,menuid,firsted,thiselement) {
    	// var reg=new RegExp("^#");     
    	//if(!reg.test(divid)) divid = '#'+divid;
    	//App.startPageLoading({message: '努力加载中...'});
    	App.blockUI({message: '努力加载中...'});
    	 
    	 
    	 
        $(".page-content").load(url,function(responseTxt,statusTxt,xhr){
        	//App.stopPageLoading();
        	//App.unblockUI();
	    	window.setTimeout(function() {
	           // App.stopPageLoading();
	    		App.unblockUI();
	        }, 10);
             
          if(statusTxt=="error" )
          {
        	  if(xhr.status == 403)
    		  {
        		  if(menuid != '')
        			  $(".page-content").load('../jsp/common/403.jsp?menupath_menuid='+menuid);
        		  else
        			  $(".page-content").load('../jsp/common/403.jsp');
    		  }
        	  else if(xhr.status > 400  &&  xhr.status < 500)
    		  {
        		  if(menuid != '')
        			  $(".page-content").load('../jsp/common/404.jsp?menupath_menuid='+menuid);
        		  else
        			  $(".page-content").load('../jsp/common/404.jsp');
    		  }
        	  else if(xhr.status >= 500 && xhr.status < 600)
    		  {
        		  if(menuid != '')
        			  $(".page-content").load('../jsp/common/500.jsp?menupath_menuid='+menuid);
        		  else
        			  $(".page-content").load('../jsp/common/500.jsp');
    		  }
        	  
          }
          
        
          if(firsted){
        	  var resBreakpointMd = App.getResponsiveBreakpoint('md');
        	  if(menuid != '')
        	  {
        		  var menuContainer = $('.page-sidebar ul');
                 
                  menuContainer.children('li.active').removeClass('active');
                  menuContainer.children('arrow.open').removeClass('open');
                  thiselement = $("#left__"+menuid);
                  thiselement.parents('li').each(function () {
                      $(this).addClass('active');
                      $(this).children('a > span.arrow').addClass('open');
                  });
                  thiselement.parents('li').addClass('active');

                 
        	  }	 
        	  if (App.getViewPort().width < resBreakpointMd && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page 
                  $('.page-header .responsive-toggler').click();
              } 
        	  
          }
          else
    	  {
    	  	 Layout.fixContentHeight(); // fix content height
             App.initAjax(); // initialize core stuff
             if(!firsted){
           	  	 var menuContainer = $('.page-sidebar ul');
                 var resBreakpointMd = App.getResponsiveBreakpoint('md');
                 menuContainer.children('li.active').removeClass('active');
                // menuContainer.children('arrow.open').removeClass('open');
                 $(thiselement).parents('li').each(function () {
                       $(this).addClass('active');
                        $(this).children('a > span.arrow').addClass('open');
                 });
                $(thiselement).parents('li').addClass('active');
  
                if (App.getViewPort().width < resBreakpointMd && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page 
                    $('.page-header .responsive-toggler').click();
	            } 
             }
    	  }
        });
        return false;
        
    }
    
    var clicknourlmodule = function(thiselement) {
    	 
    	
            	  var menuContainer = $('.page-sidebar ul');
                  var resBreakpointMd = App.getResponsiveBreakpoint('md');
                  menuContainer.children('li.active').removeClass('active');
                  menuContainer.children('arrow.open').removeClass('open');

                  $(thiselement).parents('li').each(function () {
                      $(this).addClass('active');
                      $(this).children('a > span.arrow').addClass('open');
                  });
                  $(thiselement).parents('li').addClass('active');

                  if (App.getViewPort().width < resBreakpointMd && $('.page-sidebar').hasClass("in")) { // close the menu on mobile view while laoding a page 
                      $('.page-header .responsive-toggler').click();
                  } 
                  return false;
             
        
    }
    
    var initTheme = function()
    {
    	Demo.init(); // init metronic core componets
    }

    
  

    return {
        //main function to initiate the module
    	gotoworkspace: function(divid,url,menuid,firsted,thiselement) {

    		gotoworkspace(divid,url,menuid,firsted,thiselement);

        },
        clicknourlmodule:function(thiselement){
        	clicknourlmodule(thiselement);
        },
        initTheme: function() {
	
        	initTheme();
	
	    },
	    changeFrameHeight:function(id){
	    	//alert($(".page-content").height());
	    	//alert($(".page-content").width());
	    	//alert($(".page-content").outerHeight());
	    	//alert($(".page-content").outerWidth());
	    //	var height = App.getViewPort().height - $('.page-header').outerHeight(true);
//$('#product-source').css('height', height);
	    	$("#"+id).height($(".page-content").height());
	    	// $("#"+id).height(height);
	    	 $("#"+id).width($(".page-content").width()+15);
	    	 
	    	//parent.height=id.document.body.scrollHeight+10;
	    	//id.document.body.scrollWidth = parent.width;
	    },
	    gotomenu:function(menuid,thiselement,e)
	    {
	    	gotomenu(menuid,thiselement,e);
	    },
	    changeModelFrameHeight:function(iframeid,modelwindow){
	    	
	    	$("#"+iframeid).height($(modelwindow).height());
	    	// $("#"+id).height(height);
	    	 $("#"+iframeid).width($(modelwindow).width()+15);
	    }
	    

    };

}();

