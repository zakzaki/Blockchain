package Architecture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Blockchain implements Serializable , Sendable {

    private List<Block> blockChain;
    private int validatorsNumber;


    public Blockchain(){
        super();
        this.blockChain = new ArrayList<>();
    }
    public Blockchain(List<Block> blocks){
        super();
        this.blockChain = blocks;
    }

    //Vérifier la validité de la Blockchain
    public boolean isBlockChainValid() {
        if (blockChain.size() > 1) {
            for (int i = 1; i <= blockChain.size()-1; i++) {
                Block currentBlock = (Block) blockChain.get(i-1);
                Block nextBlock = (Block) blockChain.get(i);
                if (!(nextBlock.getSerialiser().getHash_prev_block().equals(currentBlock.currentHash))) {
                    return false;
                }
            }
        }
        return true;
    }

    //Ajouter un nouveau Bloc à la Blockchain
    public void addBlock(Block b) {
        if (isBlockValid(b)) 
            blockChain.add(b);
       // else System.out.println("Le bloc n'est pas valide, on ne peut pas ajouter un bloc");
    }

    //Retourner la liste de tous les blocs de la Blockchain
    public List<Block> getBlocks()
    {
        return blockChain;
    }

    // retrouner le bloc N° i de la Blockchain
    public Block getBlock(int i)
    {
        return blockChain.get(i);
    }

    //Retourne le dernier Bloc de la Blockchain
    public Block getLastBlock()
    {
        return blockChain.get(blockChain.size()-1);
    }

    // Retourne la taille de la Blockchain
    public int size()
    {
        return blockChain.size();
    }

 


    public Block getLatestBlock() {
        if (size()==0) {
            return null;
        }
        return getBlock(size() - 1);
    }



    private boolean isBlockValid(final Block block) {
        final Block latestBlock = getLatestBlock();

        if (latestBlock == null) {

            if (block.isGenesis())
                return true;
            else
                return false;
        }
        if (!Objects.equals(block.getSerialiser().getHash_prev_block(), latestBlock.getCurrentHash())) {
            System.out.println("Unmatched hash code");
            return false;
        }

        return true;
    }

    public String getStringResume() {
        return String.valueOf(blockChain.size()) + " blocks ";
    }

    public int getValidatorsNumber() {
        return validatorsNumber;
    }
}
