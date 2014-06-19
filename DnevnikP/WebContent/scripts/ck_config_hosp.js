CKEDITOR.editorConfig = function(config) {
    config.extraPlugins = 'uicolor';
    config.resize_enabled = false;
    config.toolbar = 'MyToolbar';
    config.toolbar_MyToolbar = [ [ 'Bold','Italic','Underline','-','Subscript','Superscript','-','NumberedList','BulletedList' ] ];
    config.toolbar = 'MyToolbar2';
    config.toolbar_MyToolbar2 = [ [ 'Bold','Italic','Underline' ] ];
    config.removePlugins = 'elementspath';
};

//This code could (may be should) go in your config.js file
CKEDITOR.stylesSet.add('my_custom_style', [
  { name: 'My Custom Block', element: 'h3', styles: { color: 'blue'} },
  { name: 'My Custom Inline', element: 'span', attributes: {'class': 'mine'} }
]);
// This code is for when you start up a CKEditor instance
CKEDITOR.replace( 'editor1',{
  uiColor: '#9AB8F3',
  stylesSet: 'my_custom_style'
});