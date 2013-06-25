dojo.addOnLoad(function() {
	var myStore = new dojo.data.ItemFileReadStore({
		url : "tree.json"
	});

	var myModel = new dijit.tree.ForestStoreModel({
		store : myStore,
		query : {
			"type" : "category"
		},
		/*
		 * forest store model 可以显示根节点, 设置如下属性即可 
		 * rootId: "root", 
		 * rootLabel: "Continents",
		 */
		childrenAttrs : [ "children" ]
	});

	var myTree = new dijit.Tree({
		model : myModel,
		showRoot : false,
	// onOpenClick : true,
	// onLoad : function() {
	// dojo.byId('image').src = '../resources/images/root.jpg';
	// },
	// onClick : function(item) {
	// dojo.byId('image').src = '../resources/images/' + item.id + '.jpg';
	// }
	}, "divTree");

	myTree.startup();
});