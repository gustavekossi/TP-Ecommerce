/**
 * 
 */
package beanUtils;

import java.util.ArrayList;

/**
 * @author gkossi
 *
 */
public class PanierBean {

	public PanierBean() {
		// TODO Auto-generated constructor stub
	}

	private ArrayList<ItemPanierBean>listeItem=  new ArrayList<ItemPanierBean>();
	
	public void addItem(ItemPanierBean item){
		listeItem.add(item);
	}
	/**
	 * @return the listeItem
	 */
	public ArrayList<ItemPanierBean> getListeItem() {
		return listeItem;
	}

	/**
	 * @param listeItem the listeItem to set
	 */
	public void setListeItem(ArrayList<ItemPanierBean> listeItem) {
		this.listeItem = listeItem;
	}


}
