package p2p;

import java.io.Serializable;
import java.util.List;

import Architecture.Block;
import Architecture.Blockchain;
import Architecture.Transaction;

import java.util.List;

/**
 * @author RIGHI Sylia & TUDOR Alexandra
 */
public class Message implements Serializable{

    private static final long serialVersionUID = 1L;

    int senderPort;
    int receiverPort;
    String senderHost;
    String receiverHost;
    Message.MESSAGE_TYPE type;
    List<Block> blockOlds;
    Blockchain blockchain;
    Block block;
    Transaction transaction;

    /**
     * Enum p2p messages communication protocol
     */
    public enum MESSAGE_TYPE {
        READY, REQUEST_BLOCKCHAIN, RSPONSE_BLOCKCHAIN,SEND_TRANSACTION, SEND_NEW_BLOCK, SEND_VALIDATOR_NOMINATION;
    }

    /**
     *
     * @return Transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     *
     * @return Block
     */
    public Block getBlock() {
        return block;
    }

    /**
     *
     * @return Blockchain
     */
    public Blockchain getBlockchain() {
        return blockchain;
    }

    /************************************** Overrides *****************************************************************/

    @Override
    public String toString() {
        if (blockchain != null){
            return String.format("Message {type=%s, sender=%s:%d, receiver=%s:%d, data=%s}", type, senderHost,senderPort,
                    receiverHost,receiverPort,blockchain.getStringResume());
        }else if (transaction != null){
            return String.format("Message {type=%s, sender=%s:%d, receiver=%s:%d, data=%s}", type, senderHost,senderPort,
                    receiverHost,receiverPort,transaction.toString());
        }else{
            return String.format("Message {type=%s, sender=%s:%d, receiver=%s:%d, data=NULL}", type, senderHost,senderPort,
                    receiverHost,receiverPort);
        }

    }

    /*************************************   Inner class Message Builder **********************************************/

    static class MessageBuilder {

        private final Message message = new Message();

        /**
         *
         * @param host
         * @param port
         * @return
         */
        Message.MessageBuilder withSender(final String host,final int port) {
            message.senderHost = host;
            message.senderPort = port;
            return this;
        }

        /**
         *
         * @param host
         * @param port
         * @return
         */
        Message.MessageBuilder withReceiver(final String host,final int port) {
            message.receiverHost = host;
            message.receiverPort = port;
            return this;
        }

        /**
         *
         * @param type
         * @return
         */
        Message.MessageBuilder withType(final Message.MESSAGE_TYPE type) {
            message.type = type;
            return this;
        }

        /**
         *
         * @param blockOlds
         * @return
         */
        Message.MessageBuilder withBlocks(final List<Block> blockOlds) {
            message.blockOlds = blockOlds;
            return this;
        }

        /**
         *
         * @param blockchain
         * @return
         */
        Message.MessageBuilder withBlockchain(final Blockchain blockchain){
            message.blockchain = blockchain;
            return this;
        }

        /**
         *
         * @param block
         * @return
         */
        Message.MessageBuilder withBlock(final Block block){
            message.block = block;
            return this;
        }

        /**
         *
         * @param transaction
         * @return
         */
        Message.MessageBuilder withTransaction(final Transaction transaction){
            message.transaction = transaction;
            return this;
        }

        /**
         *
         * @return
         */
        Message build() {
            return message;
        }

    }



}