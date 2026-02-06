let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[@class='oneRecordHomeFlexipage2Wrapper']//one-record-home-flexipage2", document);
let listItem = document.evaluate(".//records-record-layout-item | .//flexipage-field", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    let label = xpathSingleEval(".//span[@class='test-id__field-label'] | .//span[contains(@class, 'slds-form-element__label')]", listNode);
    let button = xpathSingleEval(".//button[contains(@class, 'inline-edit-trigger')]", listNode);
    let checkbox = xpathSingleEval(".//span[contains(@class,'slds-checkbox_faux')]", listNode);
    if (label && button) {
        map.set(label.textContent, button);
    }else if(label && checkbox){
        map.set(label.textContent, checkbox);
    }
    listNode = listItem.iterateNext();
}
console.log(map);

return Object.fromEntries(map);