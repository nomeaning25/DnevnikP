FCKeditor.ToolbarSets["Simple"] = [
                                   ['Bold','TextColor']
                                 ];

FCKeditor.ToolbarStartExpanded = false;
 
FCKeditor.editorConfig = function(config) {
	    config.extraPlugins = 'uicolor';
	    config.resize_enabled = false;
	    config.toolbar = 'MyToolbar';
	    config.toolbar_MyToolbar = [
	            [ 'Bold', 'Italic', 'Underline', 'Subscript',
	                    'Superscript', 'TextColor', 'FontSize','-', 'Link', 'Unlink' ],
	            [ 'Undo', 'Redo', '-', 'JustifyLeft', 'JustifyCenter',
	                    'JustifyRight', 'JustifyBlock' ] ];
	};
