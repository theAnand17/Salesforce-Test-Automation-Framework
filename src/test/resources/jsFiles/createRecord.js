let map = new Map();
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
    let input = xpathSingleEval(".//input", listNode);
    let comboBox = xpathSingleEval(".//lightning-base-combobox", listNode);
    let dropdown = xpathSingleEval(".//lightning-base-combobox//button",listNode);
    let textarea = xpathSingleEval(".//textarea",listNode);

    if (label && input) {
     map.set(label.textContent, input);
     if (comboBox){
         let comboText = xpathSingleEval(".//lightning-base-combobox-formatted-text", comboBox);
         if (comboText) {
                         console.log(comboText.innerText)
                         map.set(label.textContent + " suggestion", comboText);
                        }
       }
       }else if(dropdown)
         {
            map.set(label.textContent,dropdown);
         }

    let addressNode = xpathSingleEval(".//lightning-input-address",listNode);
    if(addressNode)
    {
      let addressTextarea = xpathSingleEval(".//textarea", addressNode);
      let addressTextareaLabel = xpathSingleEval(".//lightning-textarea/label", addressNode);
      if(addressTextarea && addressTextareaLabel){
           map.set(addressTextareaLabel.textContent,addressTextarea);
        }
    }
    if(label && textarea){
     map.set(label.textContent,textarea);
     }
    let listItemAddressFields = document.evaluate(".//lightning-input-address//lightning-input", listNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
    let LightningInputNode = listItemAddressFields.iterateNext();
    while(LightningInputNode) {
      let lightingAddressLabel = xpathSingleEval(".//label", LightningInputNode);
      let lightingAddressInputAddress = xpathSingleEval(".//input", LightningInputNode);
      if (lightingAddressLabel && lightingAddressInputAddress) {
            map.set(lightingAddressLabel.textContent, lightingAddressInputAddress);
          }
      LightningInputNode = listItemAddressFields.iterateNext();
     }

    let nameSection     = document.evaluate(".//div[@class='slds-form-element__row']",listNode,null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
    let nameSectionNode = nameSection.iterateNext();
    while(nameSectionNode)
      {
        label = xpathSingleEval(".//label",nameSectionNode);
        input = xpathSingleEval(".//input",nameSectionNode);
        if(label &&input)
         {
           map.set(label.textContent,input);
         }
        nameSectionNode = nameSection.iterateNext();
      }
    listNode = listItem.iterateNext();
}

let contactRoleColumns     = document.evaluate(".//div[contains(@class,'slds-dueling-list__column')]",rootNode,null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let contactRoleColumnsNode = contactRoleColumns.iterateNext();
while(contactRoleColumnsNode)
{
  let contactRoleColumnsLabel  = xpathSingleEval(".//span",contactRoleColumnsNode);
  if(contactRoleColumnsLabel)
  {
     let dropDownOptions          = document.evaluate(".//div//li//span/span",contactRoleColumnsNode,null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
     let dropDownOptionsNode      = dropDownOptions.iterateNext();
     let items = [];
     while(dropDownOptionsNode)
      {
        items.push(dropDownOptionsNode);
        dropDownOptionsNode      = dropDownOptions.iterateNext();
      }
     map.set(contactRoleColumnsLabel.textContent,items);
  }
 contactRoleColumnsNode = contactRoleColumns.iterateNext();
}

let selectIcon     = document.evaluate(".//div[contains(@class,'slds-dueling-list__column')]//lightning-button-icon",rootNode,null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let selectIconNode = selectIcon.iterateNext();
while(selectIconNode)
  {
    let selectIconLabel = xpathSingleEval(".//span",selectIconNode);
    let selectButton    = xpathSingleEval(".//button",selectIconNode);
    if(selectIconLabel && selectButton)
     {
      map.set(selectIconLabel.textContent, selectButton);
     }
    selectIconNode = selectIcon.iterateNext();
  }

console.log(map);

return Object.fromEntries(map);