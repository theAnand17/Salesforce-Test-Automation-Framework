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
    let text = xpathSingleEval(".//lightning-formatted-text | .//span[@class='displayLabel']/slot | .//lightning-formatted-number", listNode);
    let checkBox = xpathSingleEval(".//lightning-input//input", listNode);
    let address = xpathSingleEval(".//lightning-formatted-address//a", listNode);
    let lookUps = xpathSingleEval(".//force-lookup//a", listNode);
    let comboBox = xpathSingleEval(".//ul//lightning-base-combobox-item", listNode);
    let emailText = xpathSingleEval(".//emailui-formatted-email-account//a", listNode);
    let phoneText = xpathSingleEval(".//lightning-formatted-phone", listNode);
    if (label && text) {
      map.set(label.textContent, text);
    }if (label && address) {
      map.set(label.textContent, address);
    }if (label && lookUps) {
      map.set(label.textContent, lookUps);
    }if (label && checkBox) {
      map.set(label.textContent, checkBox);
    }if (label && phoneText) {
      map.set(label.textContent,phoneText);
    }if (label && emailText) {
      map.set(label.textContent,emailText);
    }
    listNode = listItem.iterateNext();
}
console.log(map)

return Object.fromEntries(map);