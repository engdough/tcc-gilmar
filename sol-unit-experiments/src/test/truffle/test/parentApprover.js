const ParentApprover = artifacts.require("ParentApprover");

contract('ParentApprover', (accounts) => {


    
  function millisToMinutesAndSeconds(millis) {
    var minutes = Math.floor(millis / 60000);
    var seconds = ((millis % 60000) / 1000).toFixed(0);
    return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
  }

  //instancia atual do contrato
  var contract_instance;

  const parent = accounts[0];
  const child = accounts[1];

  beforeEach(async () => {
    var inicio = new Date(); 
    contract_instance = await ParentApprover.new();

    await contract_instance.setChild(child);
    await contract_instance.setParent(parent);

    const destinationAddress = accounts[3];
    await contract_instance.submitTransaction(destinationAddress, web3.utils.toWei('1', 'ether'));

    var fim = new Date(); 
    var Difference_In_Time = fim.getTime() - inicio.getTime(); 
    console.info("beforeEach: (" + Difference_In_Time + "ms)" );
    console.info("beforeEach (min:seg): " + millisToMinutesAndSeconds(Difference_In_Time)  );
  });

  it('Parent can approve transaction', async () => {

      const destinationAddress = accounts[3];
      const valueToSend = web3.utils.toWei('0.1', 'ether');
      const destinationBalanceBefore = await web3.eth.getBalance(destinationAddress);
      
      await contract_instance.submitTransaction(destinationAddress, web3.utils.toWei('0.1', 'ether'));
      await contract_instance.executeTransaction(1);

      const destinationBalanceAfter = await web3.eth.getBalance(destinationAddress);

      console.info(destinationBalanceAfter);

  });

  it("Parent can get details of transaction to approve", async function() {
       let tuple3 = await contract_instance.getTransactionDetail(1);
       assert.equal(tuple3[0], accounts[3]);
  });

  it("Parent can get list of transactions to approve", async function() {
       let lista = await contract_instance.getTransactionsToApprove();
       assert.equal(lista.length, 2);

  });

  it("Parent should not be able to approve same transation twice", async function() {
    await contract_instance.executeTransaction(1);
    await contract_instance.executeTransaction(1);
  });

  it("should be able to view parent address", async function() {
    let acParent = await contract_instance.getParent();
    assert.equal(acParent, parent);
  });

  it("should be able to view child address", async function() {
    let acChild = await contract_instance.getChild();
    assert.equal(acChild, child);
  });

});
