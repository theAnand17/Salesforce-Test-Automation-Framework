let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[@id='wrapper-body']", document);
let listItem = document.evaluate(".//flowruntime-display-text-lwc", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode)
 {
    let label = xpathSingleEval(".//span", listNode);
    let input = xpathSingleEval(".//span", listNode);


    if (label && input) {
     map.set(label.textContent, input);

      }

    listNode = listItem.iterateNext();
}

console.log(map);

return Object.fromEntries(map);
