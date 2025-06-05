package fe.cmn.data;

import com.leavay.ms.tool.CmnUtil;

import cson.core.CsonPojo;
import flutter.coder.annt.FlutterCode;
import flutter.coder.annt.FlutterToString;

@FlutterToString("return value==null?'$key':'$value';") // 为flutter提供toString逻辑
@FlutterCode("\n\t@override\n\tint get hashCode => key.hashCode;\n\n\tPairDto.pair(this.key, {this.value}){setObjectType(JAVA_TYPE);}\n@override\n\tbool compare(dynamic other) {if(other is PairDto){return JavaFactory.equals(this.key, other.key);}return false;}"
		+ "\nPairDto convertToPairDto(){\r\nPairDto pair = PairDto();\r\nfor (String fd in pair.getFields()) {\r\npair.setField(fd,this.getField(fd));\r\n}\r\nreturn pair;\r\n}") // 重载hashCode，以key为准，为flutter生成方便的构造代码
public class PairDto<K, V> extends FePojo
{
    private static final long serialVersionUID = -5756551274077291085L;

    K key;

    V value;
    

    public PairDto()
    {

    }

    public PairDto(K key)
    {
        setKey(key);
    }

    public PairDto(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    public K getKey()
    {
        return key;
    }

    public void setKey(K key)
    {
        this.key = key;
    }

    public V getValue()
    {
        return value;
    }

    public void setValue(V value)
    {
        this.value = value;
    }

    public String toString()
    {
        return CmnUtil.getNameAndLabel(CmnUtil.getString(key, null), CmnUtil.getString(value, null));
    }
}
