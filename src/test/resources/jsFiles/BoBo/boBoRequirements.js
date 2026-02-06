let map = new Map();

function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

let rootNode = xpathSingleEval("//div[@id='wrapper-body']", document);
let listItem = document.evaluate(".//flowruntime-input-wrapper2", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();

while (listNode) {
    let label = xpathSingleEval(".//lightning-formatted-rich-text/span[@part='formatted-rich-text']/text()", listNode);
    let checkBox = xpathSingleEval(".//lightning-input//label//span", listNode);

    if (label && checkBox) {
        map.set(label.textContent.trim(), checkBox);
    }

    listNode = listItem.iterateNext();
}

console.log(map);

return Object.fromEntries(map);
