package experiments.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.5.0.
 */
public class Congress extends Contract {
    private static final String BINARY = "60806040523480156200001157600080fd5b5060008054600160a060020a031916331781556040805160208101909152818152620000479190640100000000620000a2810204565b60005460408051808201909152600781527f666f756e6465720000000000000000000000000000000000000000000000000060208201526200009c91600160a060020a031690640100000000620000a2810204565b62000349565b60008054600160a060020a03163314620000bb57600080fd5b50600160a060020a0382166000908152600660205260409020548015156200010f5760078054600160a060020a0385166000908152600660205260409020819055906200010c9060018301620001e5565b90505b60606040519081016040528084600160a060020a03168152602001838152602001428152506007828154811015156200014457fe5b60009182526020918290208351600392909202018054600160a060020a031916600160a060020a039092169190911781558282015180519192620001919260018501929091019062000219565b506040918201516002909101558051600160a060020a03851681526001602082015281517f27b022af4a8347100c7a041ce5ccf8e14d644ff05de696315196faae8cd50c9b929181900390910190a1505050565b81548183558181111562000214576003028160030283600052602060002091820191016200021491906200029e565b505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200025c57805160ff19168380011785556200028c565b828001600101855582156200028c579182015b828111156200028c5782518255916020019190600101906200026f565b506200029a929150620002e1565b5090565b620002de91905b808211156200029a578054600160a060020a03191681556000620002cd6001830182620002fe565b5060006002820155600301620002a5565b90565b620002de91905b808211156200029a5760008155600101620002e8565b50805460018160011615610100020316600290046000825580601f1062000326575062000346565b601f016020900490600052602060002090810190620003469190620002e1565b50565b61191980620003596000396000f3006080604052600436106100fb5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663013cf08b81146101375780630b1ca49a14610215578063237e9492146102385780633910682114610296578063400e3949146102c95780635daf08ca146102de57806369bd3436146103845780638160f0b5146103995780638da5cb5b146103ae5780638f4ffcb1146103df578063aa02a90f1461044f578063b1050da514610464578063b9f256cd1461050b578063bcca1fd3146105b2578063c127c247146105d0578063d3c0715b14610637578063eceb294514610699578063f2fde38b1461071c575b6040805133815234602082015281517fa398b89ba344a0b23a0b9de53db298b2a1a868b396c1878b7e9dcbafecd49b13929181900390910190a1005b34801561014357600080fd5b5061014f60043561073d565b60408051600160a060020a038b16815260208082018b905260608201899052871515608083015286151560a083015260c0820186905260e0820185905261010082018490526101209282018381528a519383019390935289519192916101408401918b019080838360005b838110156101d25781810151838201526020016101ba565b50505050905090810190601f1680156101ff5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390f35b34801561022157600080fd5b50610236600160a060020a0360043516610834565b005b34801561024457600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526102369583359536956044949193909101919081908401838280828437509497506109ea9650505050505050565b3480156102a257600080fd5b506102b7600160a060020a0360043516610c7d565b60408051918252519081900360200190f35b3480156102d557600080fd5b506102b7610c8f565b3480156102ea57600080fd5b506102f6600435610c95565b60408051600160a060020a0385168152908101829052606060208083018281528551928401929092528451608084019186019080838360005b8381101561034757818101518382015260200161032f565b50505050905090810190601f1680156103745780820380516001836020036101000a031916815260200191505b5094505050505060405180910390f35b34801561039057600080fd5b506102b7610d60565b3480156103a557600080fd5b506102b7610d66565b3480156103ba57600080fd5b506103c3610d6c565b60408051600160a060020a039092168252519081900360200190f35b3480156103eb57600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261023694600160a060020a03813581169560248035966044359093169536956084949201918190840183828082843750949750610d7b9650505050505050565b34801561045b57600080fd5b506102b7610f00565b34801561047057600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526102b7948235600160a060020a031694602480359536959460649492019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750610f069650505050505050565b34801561051757600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526102b7948235600160a060020a031694602480359536959460649492019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506111749650505050505050565b3480156105be57600080fd5b506102366004356024356044356111ae565b3480156105dc57600080fd5b5060408051602060046024803582810135601f8101859004850286018501909652858552610236958335600160a060020a03169536956044949193909101919081908401838280828437509497506112199650505050505050565b34801561064357600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526102b794823594602480351515953695946064949201919081908401838280828437509497506113559650505050505050565b3480156106a557600080fd5b50604080516020601f60643560048181013592830184900484028501840190955281845261070894803594600160a060020a0360248035919091169560443595369560849493019181908401838280828437509497506114d09650505050505050565b604080519115158252519081900360200190f35b34801561072857600080fd5b50610236600160a060020a03600435166115f8565b600480548290811061074b57fe5b6000918252602091829020600a91909102018054600180830154600280850180546040805161010096831615969096026000190190911692909204601f8101889004880285018801909252818452600160a060020a03909416965090949192918301828280156107fc5780601f106107d1576101008083540402835291602001916107fc565b820191906000526020600020905b8154815290600101906020018083116107df57829003601f168201915b50505060038401546004850154600586015460068701546007909701549596929560ff80841696506101009093049092169350919089565b60008054600160a060020a0316331461084c57600080fd5b600160a060020a038216600090815260066020526040902054151561087057600080fd5b50600160a060020a0381166000908152600660205260409020545b600754600019018110156109725760078054600183019081106108aa57fe5b90600052602060002090600302016007828154811015156108c757fe5b6000918252602090912082546003909202018054600160a060020a031916600160a060020a039092169190911781556001808301805461091e92808501929160026000199282161561010002929092011604611631565b5060028201548160020155905050806006600060078481548110151561094057fe5b60009182526020808320600390920290910154600160a060020a0316835282019290925260400190205560010161088b565b600160a060020a03821660009081526006602052604081205560078054600019810190811061099d57fe5b6000918252602082206003909102018054600160a060020a0319168155906109c860018301826116b6565b5060006002919091015560078054906109e59060001983016116fd565b505050565b6000806004848154811015156109fc57fe5b90600052602060002090600a02019150816003015442118015610a245750600482015460ff16155b8015610b275750815460018301546040516c01000000000000000000000000600160a060020a0390931692830260208083019182526034830184905287518893605401918401908083835b60208310610a8e5780518252601f199092019160209182019101610a6f565b6001836020036101000a03801982511681845116808217855250505050505090500193505050506040516020818303038152906040526040518082805190602001908083835b60208310610af35780518252601f199092019160209182019101610ad4565b5181516020939093036101000a60001901801990911692169190911790526040519201829003909120600786015414925050505b8015610b395750600154826005015410155b1515610b4457600080fd5b60035482600601541315610c095760048201805460ff191660019081179091558254908301546040518551600160a060020a03909316928691908190602084019080838360005b83811015610ba3578181015183820152602001610b8b565b50505050905090810190601f168015610bd05780820380516001836020036101000a031916815260200191505b5091505060006040518083038185875af1925050509050801515610bf357600080fd5b60048201805461ff001916610100179055610c17565b60048201805461ff00191690555b6006820154600583015460048401546040805188815260208101949094528381019290925260ff6101009091041615156060830152517fd220b7272a8b6d0d7d6bcdace67b936a8f175e6d5c1b3ee438b72256b32ab3af9181900360800190a150505050565b60066020526000908152604090205481565b60055481565b6007805482908110610ca357fe5b600091825260209182902060039091020180546001808301805460408051601f6002600019968516156101000296909601909316949094049182018790048702840187019052808352600160a060020a039093169550929390929190830182828015610d505780601f10610d2557610100808354040283529160200191610d50565b820191906000526020600020905b815481529060010190602001808311610d3357829003601f168201915b5050505050908060020154905083565b60025481565b60015481565b600054600160a060020a031681565b604080517f23b872dd000000000000000000000000000000000000000000000000000000008152600160a060020a03868116600483015230602483015260448201869052915184928316916323b872dd9160648083019260209291908290030181600087803b158015610ded57600080fd5b505af1158015610e01573d6000803e3d6000fd5b505050506040513d6020811015610e1757600080fd5b50511515610e2457600080fd5b7f0eeb71b8926d7ed8f47a2cedf6b9b204e2001344c7fa20c696c9f06ea7c413c6858585856040518085600160a060020a0316600160a060020a0316815260200184815260200183600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019080838360005b83811015610ebc578181015183820152602001610ea4565b50505050905090810190601f168015610ee95780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a15050505050565b60035481565b3360009081526006602052604081205481901515610f2357600080fd5b6004805490610f359060018301611729565b9150600482815481101515610f4657fe5b6000918252602091829020600a91909102018054600160a060020a031916600160a060020a038916178155600181018790558551909250610f8f91600284019190870190611755565b508585846040516020018084600160a060020a0316600160a060020a03166c0100000000000000000000000002815260140183815260200182805190602001908083835b60208310610ff25780518252601f199092019160209182019101610fd3565b6001836020036101000a03801982511681845116808217855250505050505090500193505050506040516020818303038152906040526040518082805190602001908083835b602083106110575780518252601f199092019160209182019101611038565b51815160209384036101000a6000190180199092169116179052604080519290940182900382206007880155600254603c024201600388015560048701805461ffff19169055600060058801819055888352600160a060020a038d16838301529382018b90526080606083018181528b51918401919091528a517f646fec02522b41e7125cfc859a64fd4f4cefd5dc3b6237ca0abe251ded1fa88197508996508d958d95508c949360a0850192908601918190849084905b8381101561112757818101518382015260200161110f565b50505050905090810190601f1680156111545780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a16001820160055550949350505050565b33600090815260066020526040812054151561118f57600080fd5b6111a58585670de0b6b3a7640000028585610f06565b95945050505050565b600054600160a060020a031633146111c557600080fd5b600183905560028290556003819055604080518481526020810184905280820183905290517fa439d3fa452be5e0e1e24a8145e715f4fd8b9c08c96a42fd82a855a85e5d57de9181900360600190a1505050565b60008054600160a060020a0316331461123157600080fd5b50600160a060020a0382166000908152600660205260409020548015156112825760078054600160a060020a03851660009081526006602052604090208190559061127f90600183016116fd565b90505b60606040519081016040528084600160a060020a03168152602001838152602001428152506007828154811015156112b657fe5b60009182526020918290208351600392909202018054600160a060020a031916600160a060020a03909216919091178155828201518051919261130192600185019290910190611755565b506040918201516002909101558051600160a060020a03851681526001602082015281517f27b022af4a8347100c7a041ce5ccf8e14d644ff05de696315196faae8cd50c9b929181900390910190a1505050565b336000908152600660205260408120548190151561137257600080fd5b600480548690811061138057fe5b600091825260208083203384526009600a90930201918201905260409091205490915060ff16156113b057600080fd5b3360009081526009820160205260409020805460ff19166001908117909155600582018054909101905583156113f05760068101805460010190556113fd565b6006810180546000190190555b7fc34f869b7ff431b034b7b9aea9822dac189a685e0b015c7d1be3add3f89128e885853386604051808581526020018415151515815260200183600160a060020a0316600160a060020a0316815260200180602001828103825283818151815260200191508051906020019080838360005b8381101561148757818101518382015260200161146f565b50505050905090810190601f1680156114b45780820380516001836020036101000a031916815260200191505b509550505050505060405180910390a160050154949350505050565b6000806004868154811015156114e257fe5b90600052602060002090600a020190508484846040516020018084600160a060020a0316600160a060020a03166c0100000000000000000000000002815260140183815260200182805190602001908083835b602083106115545780518252601f199092019160209182019101611535565b6001836020036101000a03801982511681845116808217855250505050505090500193505050506040516020818303038152906040526040518082805190602001908083835b602083106115b95780518252601f19909201916020918201910161159a565b5181516000196020949094036101000a939093019283169219169190911790526040519201829003909120600785015414945050505050949350505050565b600054600160a060020a0316331461160f57600080fd5b60008054600160a060020a031916600160a060020a0392909216919091179055565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061166a57805485556116a6565b828001600101855582156116a657600052602060002091601f016020900482015b828111156116a657825482559160010191906001019061168b565b506116b29291506117c3565b5090565b50805460018160011615610100020316600290046000825580601f106116dc57506116fa565b601f0160209004906000526020600020908101906116fa91906117c3565b50565b8154818355818111156109e5576003028160030283600052602060002091820191016109e591906117e0565b8154818355818111156109e557600a0281600a0283600052602060002091820191016109e5919061181b565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061179657805160ff19168380011785556116a6565b828001600101855582156116a6579182015b828111156116a65782518255916020019190600101906117a8565b6117dd91905b808211156116b257600081556001016117c9565b90565b6117dd91905b808211156116b2578054600160a060020a0319168155600061180b60018301826116b6565b50600060028201556003016117e6565b6117dd91905b808211156116b2578054600160a060020a031916815560006001820181905561184d60028301826116b6565b60006003830181905560048301805461ffff1916905560058301819055600683018190556007830181905561188690600884019061188f565b50600a01611821565b50805460008255600202906000526020600020908101906116fa91906117dd91905b808211156116b257805474ffffffffffffffffffffffffffffffffffffffffff1916815560006118e460018301826116b6565b506002016118b15600a165627a7a72305820c0b04ce1348669df15fe320f5ee840675742d5d7cd26fd1eb9c65e7613c35f500029";

    public static final String FUNC_PROPOSALS = "proposals";

    public static final String FUNC_REMOVEMEMBER = "removeMember";

    public static final String FUNC_EXECUTEPROPOSAL = "executeProposal";

    public static final String FUNC_MEMBERID = "memberId";

    public static final String FUNC_NUMPROPOSALS = "numProposals";

    public static final String FUNC_MEMBERS = "members";

    public static final String FUNC_DEBATINGPERIODINMINUTES = "debatingPeriodInMinutes";

    public static final String FUNC_MINIMUMQUORUM = "minimumQuorum";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RECEIVEAPPROVAL = "receiveApproval";

    public static final String FUNC_MAJORITYMARGIN = "majorityMargin";

    public static final String FUNC_NEWPROPOSAL = "newProposal";

    public static final String FUNC_NEWPROPOSALINETHER = "newProposalInEther";

    public static final String FUNC_CHANGEVOTINGRULES = "changeVotingRules";

    public static final String FUNC_ADDMEMBER = "addMember";

    public static final String FUNC_VOTE = "vote";

    public static final String FUNC_CHECKPROPOSALCODE = "checkProposalCode";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event PROPOSALADDED_EVENT = new Event("ProposalAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event VOTED_EVENT = new Event("Voted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event PROPOSALTALLIED_EVENT = new Event("ProposalTallied", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event MEMBERSHIPCHANGED_EVENT = new Event("MembershipChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    public static final Event CHANGEOFRULES_EVENT = new Event("ChangeOfRules", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}));
    ;

    public static final Event RECEIVEDETHER_EVENT = new Event("receivedEther", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event RECEIVEDTOKENS_EVENT = new Event("receivedTokens", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    protected Congress(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Congress(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Tuple9<String, BigInteger, String, BigInteger, Boolean, Boolean, BigInteger, BigInteger, byte[]>> proposals(BigInteger param0) {
        final Function function = new Function(FUNC_PROPOSALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}, new TypeReference<Int256>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple9<String, BigInteger, String, BigInteger, Boolean, Boolean, BigInteger, BigInteger, byte[]>>(
                new Callable<Tuple9<String, BigInteger, String, BigInteger, Boolean, Boolean, BigInteger, BigInteger, byte[]>>() {
                    @Override
                    public Tuple9<String, BigInteger, String, BigInteger, Boolean, Boolean, BigInteger, BigInteger, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<String, BigInteger, String, BigInteger, Boolean, Boolean, BigInteger, BigInteger, byte[]>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue(), 
                                (Boolean) results.get(4).getValue(), 
                                (Boolean) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (byte[]) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> removeMember(String targetMember) {
        final Function function = new Function(
                FUNC_REMOVEMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(targetMember)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> executeProposal(BigInteger proposalNumber, byte[] transactionBytecode) {
        final Function function = new Function(
                FUNC_EXECUTEPROPOSAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalNumber), 
                new org.web3j.abi.datatypes.DynamicBytes(transactionBytecode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> memberId(String param0) {
        final Function function = new Function(FUNC_MEMBERID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> numProposals() {
        final Function function = new Function(FUNC_NUMPROPOSALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple3<String, String, BigInteger>> members(BigInteger param0) {
        final Function function = new Function(FUNC_MEMBERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple3<String, String, BigInteger>>(
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteCall<BigInteger> debatingPeriodInMinutes() {
        final Function function = new Function(FUNC_DEBATINGPERIODINMINUTES, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> minimumQuorum() {
        final Function function = new Function(FUNC_MINIMUMQUORUM, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> receiveApproval(String _from, BigInteger _value, String _token, byte[] _extraData) {
        final Function function = new Function(
                FUNC_RECEIVEAPPROVAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_from), 
                new org.web3j.abi.datatypes.generated.Uint256(_value), 
                new org.web3j.abi.datatypes.Address(_token), 
                new org.web3j.abi.datatypes.DynamicBytes(_extraData)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> majorityMargin() {
        final Function function = new Function(FUNC_MAJORITYMARGIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Int256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> newProposal(String beneficiary, BigInteger weiAmount, String jobDescription, byte[] transactionBytecode) {
        final Function function = new Function(
                FUNC_NEWPROPOSAL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(weiAmount), 
                new org.web3j.abi.datatypes.Utf8String(jobDescription), 
                new org.web3j.abi.datatypes.DynamicBytes(transactionBytecode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> newProposalInEther(String beneficiary, BigInteger etherAmount, String jobDescription, byte[] transactionBytecode) {
        final Function function = new Function(
                FUNC_NEWPROPOSALINETHER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(etherAmount), 
                new org.web3j.abi.datatypes.Utf8String(jobDescription), 
                new org.web3j.abi.datatypes.DynamicBytes(transactionBytecode)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> changeVotingRules(BigInteger minimumQuorumForProposals, BigInteger minutesForDebate, BigInteger marginOfVotesForMajority) {
        final Function function = new Function(
                FUNC_CHANGEVOTINGRULES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(minimumQuorumForProposals), 
                new org.web3j.abi.datatypes.generated.Uint256(minutesForDebate), 
                new org.web3j.abi.datatypes.generated.Int256(marginOfVotesForMajority)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addMember(String targetMember, String memberName) {
        final Function function = new Function(
                FUNC_ADDMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(targetMember), 
                new org.web3j.abi.datatypes.Utf8String(memberName)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> vote(BigInteger proposalNumber, Boolean supportsProposal, String justificationText) {
        final Function function = new Function(
                FUNC_VOTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalNumber), 
                new org.web3j.abi.datatypes.Bool(supportsProposal), 
                new org.web3j.abi.datatypes.Utf8String(justificationText)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> checkProposalCode(BigInteger proposalNumber, String beneficiary, BigInteger weiAmount, byte[] transactionBytecode) {
        final Function function = new Function(FUNC_CHECKPROPOSALCODE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(proposalNumber), 
                new org.web3j.abi.datatypes.Address(beneficiary), 
                new org.web3j.abi.datatypes.generated.Uint256(weiAmount), 
                new org.web3j.abi.datatypes.DynamicBytes(transactionBytecode)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Congress> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Congress.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Congress> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Congress.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<ProposalAddedEventResponse> getProposalAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROPOSALADDED_EVENT, transactionReceipt);
        ArrayList<ProposalAddedEventResponse> responses = new ArrayList<ProposalAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProposalAddedEventResponse typedResponse = new ProposalAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.description = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ProposalAddedEventResponse> proposalAddedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ProposalAddedEventResponse>() {
            @Override
            public ProposalAddedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROPOSALADDED_EVENT, log);
                ProposalAddedEventResponse typedResponse = new ProposalAddedEventResponse();
                typedResponse.log = log;
                typedResponse.proposalID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.recipient = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.description = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ProposalAddedEventResponse> proposalAddedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPOSALADDED_EVENT));
        return proposalAddedEventObservable(filter);
    }

    public List<VotedEventResponse> getVotedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VOTED_EVENT, transactionReceipt);
        ArrayList<VotedEventResponse> responses = new ArrayList<VotedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VotedEventResponse typedResponse = new VotedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.position = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.voter = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.justification = (String) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<VotedEventResponse> votedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, VotedEventResponse>() {
            @Override
            public VotedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VOTED_EVENT, log);
                VotedEventResponse typedResponse = new VotedEventResponse();
                typedResponse.log = log;
                typedResponse.proposalID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.position = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.voter = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.justification = (String) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<VotedEventResponse> votedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VOTED_EVENT));
        return votedEventObservable(filter);
    }

    public List<ProposalTalliedEventResponse> getProposalTalliedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PROPOSALTALLIED_EVENT, transactionReceipt);
        ArrayList<ProposalTalliedEventResponse> responses = new ArrayList<ProposalTalliedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ProposalTalliedEventResponse typedResponse = new ProposalTalliedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.proposalID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.quorum = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.active = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ProposalTalliedEventResponse> proposalTalliedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ProposalTalliedEventResponse>() {
            @Override
            public ProposalTalliedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PROPOSALTALLIED_EVENT, log);
                ProposalTalliedEventResponse typedResponse = new ProposalTalliedEventResponse();
                typedResponse.log = log;
                typedResponse.proposalID = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.result = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.quorum = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.active = (Boolean) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ProposalTalliedEventResponse> proposalTalliedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PROPOSALTALLIED_EVENT));
        return proposalTalliedEventObservable(filter);
    }

    public List<MembershipChangedEventResponse> getMembershipChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MEMBERSHIPCHANGED_EVENT, transactionReceipt);
        ArrayList<MembershipChangedEventResponse> responses = new ArrayList<MembershipChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MembershipChangedEventResponse typedResponse = new MembershipChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.member = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.isMember = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<MembershipChangedEventResponse> membershipChangedEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, MembershipChangedEventResponse>() {
            @Override
            public MembershipChangedEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MEMBERSHIPCHANGED_EVENT, log);
                MembershipChangedEventResponse typedResponse = new MembershipChangedEventResponse();
                typedResponse.log = log;
                typedResponse.member = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.isMember = (Boolean) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<MembershipChangedEventResponse> membershipChangedEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MEMBERSHIPCHANGED_EVENT));
        return membershipChangedEventObservable(filter);
    }

    public List<ChangeOfRulesEventResponse> getChangeOfRulesEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANGEOFRULES_EVENT, transactionReceipt);
        ArrayList<ChangeOfRulesEventResponse> responses = new ArrayList<ChangeOfRulesEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChangeOfRulesEventResponse typedResponse = new ChangeOfRulesEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newMinimumQuorum = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newDebatingPeriodInMinutes = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.newMajorityMargin = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChangeOfRulesEventResponse> changeOfRulesEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChangeOfRulesEventResponse>() {
            @Override
            public ChangeOfRulesEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANGEOFRULES_EVENT, log);
                ChangeOfRulesEventResponse typedResponse = new ChangeOfRulesEventResponse();
                typedResponse.log = log;
                typedResponse.newMinimumQuorum = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.newDebatingPeriodInMinutes = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.newMajorityMargin = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ChangeOfRulesEventResponse> changeOfRulesEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANGEOFRULES_EVENT));
        return changeOfRulesEventObservable(filter);
    }

    public List<ReceivedEtherEventResponse> getReceivedEtherEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEDETHER_EVENT, transactionReceipt);
        ArrayList<ReceivedEtherEventResponse> responses = new ArrayList<ReceivedEtherEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedEtherEventResponse typedResponse = new ReceivedEtherEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ReceivedEtherEventResponse> receivedEtherEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ReceivedEtherEventResponse>() {
            @Override
            public ReceivedEtherEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEDETHER_EVENT, log);
                ReceivedEtherEventResponse typedResponse = new ReceivedEtherEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ReceivedEtherEventResponse> receivedEtherEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEDETHER_EVENT));
        return receivedEtherEventObservable(filter);
    }

    public List<ReceivedTokensEventResponse> getReceivedTokensEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RECEIVEDTOKENS_EVENT, transactionReceipt);
        ArrayList<ReceivedTokensEventResponse> responses = new ArrayList<ReceivedTokensEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ReceivedTokensEventResponse typedResponse = new ReceivedTokensEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse._token = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._extraData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ReceivedTokensEventResponse> receivedTokensEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ReceivedTokensEventResponse>() {
            @Override
            public ReceivedTokensEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(RECEIVEDTOKENS_EVENT, log);
                ReceivedTokensEventResponse typedResponse = new ReceivedTokensEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse._token = (String) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse._extraData = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<ReceivedTokensEventResponse> receivedTokensEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(RECEIVEDTOKENS_EVENT));
        return receivedTokensEventObservable(filter);
    }

    public static Congress load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Congress(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Congress load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Congress(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ProposalAddedEventResponse {
        public Log log;

        public BigInteger proposalID;

        public String recipient;

        public BigInteger amount;

        public String description;
    }

    public static class VotedEventResponse {
        public Log log;

        public BigInteger proposalID;

        public Boolean position;

        public String voter;

        public String justification;
    }

    public static class ProposalTalliedEventResponse {
        public Log log;

        public BigInteger proposalID;

        public BigInteger result;

        public BigInteger quorum;

        public Boolean active;
    }

    public static class MembershipChangedEventResponse {
        public Log log;

        public String member;

        public Boolean isMember;
    }

    public static class ChangeOfRulesEventResponse {
        public Log log;

        public BigInteger newMinimumQuorum;

        public BigInteger newDebatingPeriodInMinutes;

        public BigInteger newMajorityMargin;
    }

    public static class ReceivedEtherEventResponse {
        public Log log;

        public String sender;

        public BigInteger amount;
    }

    public static class ReceivedTokensEventResponse {
        public Log log;

        public String _from;

        public BigInteger _value;

        public String _token;

        public byte[] _extraData;
    }
}
