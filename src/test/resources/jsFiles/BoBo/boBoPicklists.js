let map = new Map();

function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}

let rootNode = xpathSingleEval("//div[@id='wrapper-body']", document);
let listItem = document.evaluate(".//flowruntime-picklist-input-lwc", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();

while (listNode) {
    let label = xpathSingleEval(".//lightning-formatted-rich-text//span", listNode);
    if (label) {
        let picklist = document.evaluate(".//lightning-select//select[@class='slds-select']//option", listNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
        let picklistNode = picklist.iterateNext();
        let options = [];
        while (picklistNode) {
            options.push(picklistNode);
            picklistNode = picklist.iterateNext();
        }
        map.set(label.textContent, options)
    }
    listNode = listItem.iterateNext();
}

console.log(map);

return Object.fromEntries(map);
