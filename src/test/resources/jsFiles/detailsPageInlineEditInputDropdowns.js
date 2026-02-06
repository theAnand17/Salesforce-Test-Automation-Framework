let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[@class='oneRecordHomeFlexipage2Wrapper']//one-record-home-flexipage2", document);
let listItem = document.evaluate(".//records-record-layout-item | .//flexipage-field", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    let label = xpathSingleEval(".//lightning-picklist//label", listNode);
    if (label) {
        let picklist = document.evaluate(".//lightning-picklist//lightning-base-combobox-item", listNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
        let picklistNode = picklist.iterateNext();
        let options=[];
        while(picklistNode) {
            let option = xpathSingleEval(".//span[@class='slds-truncate']", picklistNode);
            options.push(option);
            picklistNode = picklist.iterateNext();
        }
        map.set(label.textContent, options)
    }
    listNode = listItem.iterateNext();
}
console.log(map);

return Object.fromEntries(map);