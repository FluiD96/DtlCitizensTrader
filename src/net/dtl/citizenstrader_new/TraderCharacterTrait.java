package net.dtl.citizenstrader_new;


import net.citizensnpcs.api.exception.NPCLoadException;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;
import net.dtl.citizenstrader_new.traits.BankTrait;
import net.dtl.citizenstrader_new.traits.InventoryTrait;
import net.dtl.citizenstrader_new.traits.TraderTrait;

public class TraderCharacterTrait extends Trait {
	
	private CitizensTrader plugin;
	private TraderType type = TraderType.SERVER_TRADER;

	public TraderCharacterTrait() {
		super("trader");

		this.traderTrait = new TraderTrait();
		this.inventoryTrait = new InventoryTrait();
		this.bankTrait = new BankTrait();
	}
	
	@Override
	public void onSpawn() {
		if ( npc.hasTrait(TraderCharacterTrait.class) ) {
			if ( type.equals(TraderType.PLAYER_BANK) )
				CitizensTrader.getTraderManager().addBankerNpc(npc);
			else
				CitizensTrader.getTraderManager().addTraderNpc(npc);
		}

	}

	private TraderTrait traderTrait;
	private InventoryTrait inventoryTrait;
	private BankTrait bankTrait;
	
	
	public InventoryTrait getInventoryTrait() {
		return inventoryTrait;
	}
	
	public TraderTrait getTraderTrait() {
		return traderTrait;
	}
	
	public BankTrait getBankTrait() {
		return bankTrait;
	}
	
	@Override
	public void load(DataKey data) throws NPCLoadException {
		if ( data.keyExists("trader-type") ) {
			type = TraderType.getTypeByName(data.getString("trader-type"));
		}
		
		if ( type.equals(TraderType.PLAYER_BANK) )
		{
			this.bankTrait.load(data);
		}
		else
		{
			this.traderTrait.load(data);
			this.inventoryTrait.load(data);
		}
		
	}

	@Override
	public void save(DataKey data) {
		data.setString("trader-type", TraderType.toString(type));
		
		if ( type.equals(TraderType.PLAYER_BANK) )
		{
			this.bankTrait.save(data);
		}
		else
		{
			this.inventoryTrait.save(data);
			this.traderTrait.save(data);
		}
	}
	
	
	
	
	
	
	
	public enum TraderType {
		PLAYER_TRADER, SERVER_TRADER, AUCTIONHOUSE, GUILD_BANK, CUSTOM, PLAYER_BANK
;
		
		public static TraderType getTypeByName(String n) {
			if ( n.equalsIgnoreCase("server") ) 
				return TraderType.SERVER_TRADER;
			else if ( n.equalsIgnoreCase("player") )
				return TraderType.PLAYER_TRADER;
			else if ( n.equalsIgnoreCase("auctionhouse") )
				return TraderType.AUCTIONHOUSE;
			else if ( n.equalsIgnoreCase("player-bank") )
				return TraderType.PLAYER_BANK;
			return null;
		}
		
		@Override
		public String toString() {
			switch( this ) {
			case PLAYER_TRADER:
				return "player";
			case SERVER_TRADER:
				return "server";
			case AUCTIONHOUSE:
				return "auctionhouse";
			case PLAYER_BANK:
				return "player-bank";
			default:
				break;
			}
			return "";
		}
		public static String toString(TraderType w) {
			switch( w ) {
			case PLAYER_TRADER:
				return "player";
			case SERVER_TRADER:
				return "server";
			case AUCTIONHOUSE:
				return "auctionhouse";
			case PLAYER_BANK:
				return "player-bank";
			default:
				break;
			}
			return "";
		}
	}
	
	
}
