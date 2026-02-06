let map = new Map();
function xpathSingleEval(expression, contextNode) {
    return document.evaluate(expression, contextNode,
        null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;
}
let rootNode = xpathSingleEval("//div[@class='oneRecordHomeFlexipage2Wrapper']//one-record-home-flexipage2", document);
let listItem = document.evaluate(".//records-record-layout-item | .//flexipage-field", rootNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
let listNode = listItem.iterateNext();
while (listNode) {
    let label = xpathSingleEval(".//label", listNode);
    let input = xpathSingleEval(".//input", listNode);
    let comboBox = xpathSingleEval(".//lightning-base-combobox-item", listNode);
    let button = xpathSingleEval(".//button[@title='Clear Selection']", listNode);
    let dropDownLabel=xpathSingleEval(".//lightning-picklist//label", listNode);
    let dropDownButton=xpathSingleEval(".//lightning-picklist//button[contains(@class, 'slds-combobox__input')]", listNode);
    let textAreaLabel =xpathSingleEval(".//lightning-textarea//label", listNode);
    let textArea =xpathSingleEval(".//lightning-textarea//textarea", listNode);
    if (label && input) {
        if(map.has("Phone") && label.textContent=="Phone"){

        }else {
            map.set(label.textContent, input);
        }
        if(button){
            map.set(label.textContent + " clearText", button)
        }
        if (comboBox) {
            let comboText = xpathSingleEval(".//ul//span[contains(@class, 'slds-listbox__option-text')]//lightning-base-combobox-formatted-text", listNode);
            if (comboText) {
                map.set(label.textContent + " suggestion", comboText);
            }
        }
    }
    if(dropDownLabel && dropDownButton) {
        map.set(dropDownLabel.textContent, dropDownButton);
    }
    if(textAreaLabel && textArea) {
        map.set(textAreaLabel.textContent, textArea);
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

    let locations = document.evaluate(".//lightning-input-location//lightning-input", listNode, null, XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
    let locationNode = locations.iterateNext();
    while (locationNode) {
        let latLangLabel = xpathSingleEval(".//label", locationNode);
        let latLangInput = xpathSingleEval(".//input", locationNode);
        if(latLangLabel && latLangInput){
            map.set(latLangLabel.textContent, latLangInput);
        }
        locationNode=locations.iterateNext();
    }

    listNode = listItem.iterateNext();
}

console.log(map);

return Object.fromEntries(map);