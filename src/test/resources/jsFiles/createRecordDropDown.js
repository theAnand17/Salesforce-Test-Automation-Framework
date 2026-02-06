let map  = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[contains(@class, 'inlinePanel oneRecordActionWrapper')]//records-modal-lwc-detail-panel-wrapper", document);
let listItem = document.evaluate(".//records-record-layout-item", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode)
 {
    let label = xpathSingleEval(".//label", listNode);
    let dropdown = xpathSingleEval(".//lightning-base-combobox//button",listNode);

     if (label && dropdown) {
     let options = document.evaluate(".//parent::div/following-sibling::div/lightning-base-combobox-item/span/span",listNode);
     let listOptionsNode = options.iterateNext();
     let items=[];
     while(listOptionsNode) {
           items.push(listOptionsNode);
           listOptionsNode = options.iterateNext();
              }
     map.set(label.textContent, items);
     }
   listNode = listItem.iterateNext();
}
console.log(map);

return Object.fromEntries(map);