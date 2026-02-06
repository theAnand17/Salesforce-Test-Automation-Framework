let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[contains(@class,'modal-container slds-modal__container')]", document);
let listItem = document.evaluate(".//div[contains(@class,'slds-modal__content')]//lightning-layout-item | .//div[contains(@class,'slds-modal__content')]", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    if(listNode) {
        let label      = xpathSingleEval(".//label", listNode);
        let textarea   = xpathSingleEval(".//textarea", listNode);
        let text      = xpathSingleEval(".//input", listNode);
        let selectButton    = xpathSingleEval(".//button[contains(@class,'slds-combobox__input')]",listNode);
        if(label && textarea){
            map.set(label.textContent, textarea);
         }else if(label && text){
            map.set(label.textContent, text);
         }if(label && selectButton){
            map.set(label.textContent, selectButton);
         }
       }
    listNode = listItem.iterateNext();
}
console.log(map);
return Object.fromEntries(map);
