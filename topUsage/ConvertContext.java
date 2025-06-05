package gpf.dc.basic.excel;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import cell.cdao.IDao;
import crpc.CRpcContainerIntf;

public class ConvertContext implements Serializable,CRpcContainerIntf{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3911735961802295886L;

	IDao dao;
	ModelConvertorIntf modelLabelConvertor =new DefaultModelConvertor();
	Map<String,Object> context = new LinkedHashMap<>();
	
	public IDao getDao() {
		return dao;
	}
	
	public ConvertContext setDao(IDao dao) {
		this.dao = dao;
		return this;
	}
	
	public ModelConvertorIntf getModelConvertor() {
		return modelLabelConvertor;
	}
	public ConvertContext setModelConvertor(ModelConvertorIntf modelLabelConvertor) {
		this.modelLabelConvertor = modelLabelConvertor;
		return this;
	}
	
	public void putParam(String key,Object value) {
		context.put(key,value);
	}
	
	public Object getParam(String key) {
		return context.get(key);
	}
	
}
