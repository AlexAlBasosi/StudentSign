/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* global getAssetRegistry getFactory emit */

/**
 * When a student proposes a new subject, this function is triggered. The actual number of signatures for this subject is set to zero.
 * @param {org.acme.javaproject.ProposeANewSubject} tx Transaction for proposing a new subject.
 * @transaction
 */
async function proposeANewSubject(tx) {  // eslint-disable-line no-unused-vars

  // Get the asset registry for the asset.
  const assetRegistry = await getAssetRegistry('org.acme.javaproject.Subject');
  // Update the asset in the asset registry.
  await assetRegistry.update(tx.subject);
}

/**
* When a student signs a subject, this function is triggered. The actual number of signatures for this subject is incremented.
* @param {org.acme.javaproject.Sign} tx Transaction for signing a subject.
* @transaction
*/
async function sign(tx) {  // eslint-disable-line no-unused-vars

    tx.subject.actualNumberOfSignatures++;

  // Get the asset registry for the asset.
  const assetRegistry = await getAssetRegistry('org.acme.javaproject.Subject');
  // Update the asset in the asset registry.
  await assetRegistry.update(tx.subject);
}

/**
* When a faculty coordinator sets the number of signatures, this function is triggered. The expected number of signatures is set to the number of signatures the coordinator sets.
* @param {org.acme.javaproject.SetExpectedNumberOfSignatures} tx Transaction for setting the expected number of signatures.
* @transaction
*/
async function setExpectedNumberOfSignature(tx) {  // eslint-disable-line no-unused-vars

    tx.subject.expectedNumberOfSignatures = tx.newExpectedNumberOfSignatures; 

  // Get the asset registry for the asset.
  const assetRegistry = await getAssetRegistry('org.acme.javaproject.Subject');
  // Update the asset in the asset registry.
  await assetRegistry.update(tx.subject);
}

/**
* When the expected number of signatures for a subject is reached, the faculty coordinator gets notified, and if they approve the subject proposal, this function gets triggered. The system checks if the number of signatures for the particular subject is reached, if not it throws an error. 
* @param {org.acme.javaproject.ApproveSubjectProposal} tx Transaction for signing a subject.
* @transaction
*/
async function approveSubjectProposal(tx) {  // eslint-disable-line no-unused-vars

    if(tx.subject.actualNumberOfSignatures < tx.subject.expectedNumberOfSignatures){
    throw("Number of required signatures not reached!");
  } else {
    // Get the asset registry for the asset.
    const assetRegistry = await getAssetRegistry('org.acme.javaproject.Subject');
    // Update the asset in the asset registry.
    await assetRegistry.update(tx.subject);
  }  
}
