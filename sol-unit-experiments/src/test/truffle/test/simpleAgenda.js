const SimpleAgenda = artifacts.require("SimpleAgenda");

contract('SimpleAgenda', (accounts) => {

  //instancia atual do contrato
  var contract_instance;

  function toUTF8Array(str) {
    var utf8 = [];
    for (var i=0; i < str.length; i++) {
        var charcode = str.charCodeAt(i);
        if (charcode < 0x80) utf8.push(charcode);
        else if (charcode < 0x800) {
            utf8.push(0xc0 | (charcode >> 6), 
                      0x80 | (charcode & 0x3f));
        }
        else if (charcode < 0xd800 || charcode >= 0xe000) {
            utf8.push(0xe0 | (charcode >> 12), 
                      0x80 | ((charcode>>6) & 0x3f), 
                      0x80 | (charcode & 0x3f));
        }
        // surrogate pair
        else {
            i++;
            // UTF-16 encodes 0x10000-0x10FFFF by
            // subtracting 0x10000 and splitting the
            // 20 bits of 0x0-0xFFFFF into two halves
            charcode = 0x10000 + (((charcode & 0x3ff)<<10)
                      | (str.charCodeAt(i) & 0x3ff));
            utf8.push(0xf0 | (charcode >>18), 
                      0x80 | ((charcode>>12) & 0x3f), 
                      0x80 | ((charcode>>6) & 0x3f), 
                      0x80 | (charcode & 0x3f));
        }
    }
    return utf8;
  }


  function millisToMinutesAndSeconds(millis) {
    var minutes = Math.floor(millis / 60000);
    var seconds = ((millis % 60000) / 1000).toFixed(0);
    return minutes + ":" + (seconds < 10 ? '0' : '') + seconds;
  }

  beforeEach(async () => {
    var inicio = new Date(); 
    contract_instance = await SimpleAgenda.new();

    await contract_instance.newContact("Teste", toUTF8Array("1234") , "teste@gmail.com");
    await contract_instance.newContact("Teste", toUTF8Array("5678") , "teste@gmail.com");

    var fim = new Date(); 
    var Difference_In_Time = fim.getTime() - inicio.getTime(); 
    console.info("beforeEach: (" + Difference_In_Time + "ms)" );
    console.info("beforeEach (min:seg): " + millisToMinutesAndSeconds(Difference_In_Time)  );
  });

  it('possible to add a new contact', async () => {
    await contract_instance.newContact("Teste add", toUTF8Array("23") , "teste@gmail.com");
  });

  it('possible to remove a contact', async () => {
    await contract_instance.deleteContact(toUTF8Array("1234"));
  });

  it('possible to count the contacts', async () => {
    let total = await contract_instance.countContacts().valueOf();
    assert.equal(total, 2);
  });

  it('possible to get a number by his index', async () => {
    let result = await contract_instance.getNumberAtIndex( 1 );
  });

  it('possible to get a contact by his number', async () => {
    let tuple2 = await contract_instance.getContact( toUTF8Array("1234") );
    assert.equal( tuple2[0], "Teste" );
  });

});
