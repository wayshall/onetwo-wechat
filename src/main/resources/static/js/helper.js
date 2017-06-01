var helper = function () {
};
(function($) {

	//for $('e').extendMethod()
	$.extend($.fn, {
		
		isSelectedOne : function(){
            var selectedNodes = $(this).getGridSelections();
			return selectedNodes.length && selectedNodes.length==1;
		},
		
		getGridSelections: function(){
			return $(this).easyGrid('getSelections') || [];
		},
		
		easyGrid: function(dataName){
			var grid = null;
			if ($(this).data('treegrid')){
				grid = $(this).treegrid(dataName);
			} else {
				grid = $(this).datagrid(dataName);
			}
			return grid;
		}
		
	});
	
	//for $.extendMethod()
	$.extend({
		
	});

	//for helper.extendMethod()
	helper.waitingMsgState = false;
	$.extend(helper, {
		booleanFormatter: function(val, row, index){
			if(val===true || val=='true'){
				return '是';
			}else{
				return '否';
			}
		},
		
		addEmptyOptionForComboboxFilter: function(data){
			data.unshift({text:'全部', value:'', selected:true});
            return data;
		},
		
		submitEasyForm: function(config){
	    	 var _config = config || {};
	         
	    	 $(_config.dataForm).form('submit',{
	    		 url: $(_config.dataForm).attr('action'),
	             onSubmit:function(){
	                 var valid = $(this).form('enableValidation').form('validate');
	                 if(valid==true){
	        	         helper.showWaitingMsg();
	                 }
	                 return valid;
	             },
	             success: function(data){
//                	 $.messager.progress('close');
	            	 helper.closeWaitingMsg();
	                 data = JSON.parse(data);
	                 if(data.code==0){
	                	 $.messager.alert('操作出错！',data.message,'warning');
	                 }else{
	                     $.messager.alert('操作成功！',data.message,'info');
	                     
	                     $(_config.dataForm).form('reset');
	                     if(_config.dataDialog)
	                    	 $(_config.dataDialog).dialog('close');
	                     /*if(_config.datagrid){
	                    	 $(_config.datagrid).datagrid("clearSelections");
	                    	 $(_config.datagrid).datagrid("reload");
	                     }
	                     if(_config.treegrid){
	                     	 $(_config.treegrid).treegrid("clearSelections");
	                    	 $(_config.treegrid).treegrid("reload");
	                     }*/
	                     $(_config.datagrid || _config.treegrid).easyGrid('clearSelections');
	                     $(_config.datagrid || _config.treegrid).easyGrid('reload');
	                 }
	             }
	         });
	     },
	     
	     showWaitingMsg : function(msg){
	    	 var _msg = msg || '正在处理，请稍候……';
	    	 $.messager.progress({
	             title:'提示',
	             msg:_msg
	         });
	    	 helper.waitingMsgState = true;
	     },
	     
	     closeWaitingMsg : function(){
        	 $.messager.progress('close');
	    	 helper.waitingMsgState = false;
	     },
	     
	     selectOneHandler : function(datagrid, cb){
             return function(){
            	 if(!$(datagrid).isSelectedOne()){
                     $.messager.alert('警告','请选择一条数据！','warning');
                     return;
            	 }
            	 var selectedNodes = $(datagrid).getGridSelections();
                 if(cb)
                	 cb(selectedNodes[0]);
             };
	     },
	     
	     processJsonDataResult: function(config, cb){
	    	 var _config = config || {};
	    	 return function(data){
	             if(data.code==0){
	            	 $.messager.progress('close');
	            	 $.messager.alert('操作出错！',data.message,'warning');
	             }else{
	                 $.messager.progress('close');
	                 $.messager.alert('操作成功！',data.message,'info');
                     /*if(_config.datagrid){
                    	 $(_config.datagrid).datagrid("reload");
                     }
                     if(_config.treegrid){
                    	 $(_config.treegrid).treegrid("reload");
                     }*/
                     $(_config.datagrid || _config.treegrid).easyGrid('reload');
	             }
	             if(cb){
	            	 cb(data);
	             }
		     }
	     },
	     

	     deleteHandler : function(config){
	    	 return function(){
	    		 helper.__deleteHandler(config);
	    	 }
	     },
	     
	     __deleteHandler : function(config){
	    	 var _config = config || {};
             if(!_config.url){
                 $.messager.alert('警告','没有配置提交地址！','warning');
            	 return ;
             }
             
             var selectedNodes = $(_config.datagrid || _config.treegrid).getGridSelections();
             if(selectedNodes.length<1){
                 $.messager.alert('警告','请至少选择一条数据！','warning');
                 return ;
             }

         	var params = config.params || {};
             if(_config.idField){
            	 var idValues = $.map(selectedNodes, function(e){
                     return e[_config.idField];
                 });
            	 var paramIdName = _config.paramIdName || _config.idField+'s';
            	 params[paramIdName] = idValues;
             }
         	$.extend(params, config.params);

         	$.messager.confirm('警告', '确定要删除记录？', function(rs){
                 if (rs){
                	params._method =  'delete';
                	params = $.param(params, true);
                 	helper.showWaitingMsg();
                    $.post(_config.url, params, helper.processJsonDataResult(_config, function(){
                    	//clearSelections, easyui bug，删除子节点后，如果不清楚，getSelections依然会返回子节点
                    	/*if(_config.datagrid){
                    		$(_config.datagrid).datagrid("clearSelections");
                        }
                        if(_config.treegrid){
                        	$(_config.treegrid).treegrid("clearSelections");
                        }*/
                        $(_config.datagrid || _config.treegrid).easyGrid('clearSelections');
                    }));
                 }
             });
	     }
	});
	
})(jQuery);