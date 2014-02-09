var id;
var sts = new Array();
var tds;
function gridHandler(mEvent, rowid) {
	if (rowid.value != '') {
		isIE = false;
		if (mEvent.target)
			el = mEvent.target;
		else if (mEvent.srcElement) {
			el = mEvent.srcElement;
			isIE = true;
		}	
		while (el.nodeName != 'TR' && el.parentNode) {
			el = el.parentNode
		}
		if (el.nodeName == 'TR' && el.id != undefined) {
				if (el.id != '') {
					if (rowid.value != '-1' && document.getElementById(id)) {
						for (i = 0; i < tds.length; i++) {
							if (!isIE) 
								tds[i].setAttribute('style', sts[i])
							else
								tds[i].style.backgroundColor = sts[i]
						}
					}
					tds = getChildren(el, "TD");
					for (i = 0; i < tds.length; i++) {
						if (!isIE) {
							sts[i] = tds[i].getAttribute('style');
							tds[i].setAttribute('style','background-color:#99CCFF')
						}
						else {
							sts[i] = tds[i].style.backgroundColor;
							tds[i].style.backgroundColor = '#99CCFF'
						}	
					}
					id = el.id;
					rowid.value = id
				} else {
					rowid.value = '-1'
				}
				if (document.createEvent) {
					var evt = document.createEvent('HTMLEvents');
					evt.initEvent('blur', false, false);
					rowid.dispatchEvent(evt);
					var evt1 = document.createEvent('HTMLEvents');
					evt1.initEvent('change', false, false);
					rowid.dispatchEvent(evt1)
				}
				else if (document.createEventObject) {
					rowid.fireEvent('onblur');
					rowid.fireEvent('onchange')
				}
		}
	}
}
function getChildren(node, filter) {
	var result = new Array();
	var children = node.childNodes;
	for ( var i = 0; i < children.length; i++) {
		node = children[i];
		if (filter == null || node.nodeName.toUpperCase() == filter.toUpperCase() || node.nodeType == node[filter])
			result[result.length] = node
	}
	return result
}