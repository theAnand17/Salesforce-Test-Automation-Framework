let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[contains(@class, 'active')]//one-record-home-flexipage2", document);
let detailsPanel = xpathSingleEval(".//records-lwc-detail-panel", rootNode);
let recordLayout = xpathSingleEval(".//records-base-record-form//records-lwc-record-layout", detailsPanel);
let listItem = document.evaluate(".//records-record-layout-item", recordLayout, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    let label = xpathSingleEval(".//span[@class='test-id__field-label'] | .//span[contains(@class, 'slds-form-element__label')]", listNode);
    let lookUps = xpathSingleEval(".//records-hoverable-link//a//span//slot//span//slot", listNode);

    if (label && lookUps) {
      map.set(label.textContent.trim(), lookUps.textContent.trim());
    }
    listNode = listItem.iterateNext();
}
console.log(map);

return Object.fromEntries(map);