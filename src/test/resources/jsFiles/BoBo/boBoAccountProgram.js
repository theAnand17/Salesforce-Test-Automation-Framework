let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[@class='forceRelatedListDesktop']//table[@aria-label='Account Programs']", document);
let listItem = document.evaluate(".//tbody", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode)
 {
    let label = xpathSingleEval(".//th", listNode);
    let value = xpathSingleEval(".//a", listNode);

    if (label && value) {
     map.set(label.getAttribute('data-label'), value);
     }

    listNode = listItem.iterateNext();
}

console.log(map);

return Object.fromEntries(map);