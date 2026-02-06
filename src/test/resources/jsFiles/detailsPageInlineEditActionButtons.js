let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[contains(@class, 'active')]//records-base-record-form//records-form-footer", document);
let listItem = document.evaluate(".//lightning-button//button", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    if (listNode) {
      map.set(listNode.textContent, listNode);
    }
    listNode = listItem.iterateNext();
}
console.log(map)

return Object.fromEntries(map);
