package javakit.events;

public class Event
{
	protected String type;
	/** [只读] 事件的类型。类型区分大小写。 */
	public String getType() { return this.type; } 
	
	protected Object target;
	/** [只读] 事件目标。此属性包含目标节点。<br>
	 * 例如，如果用户单击“确定”按钮，则目标节点就是包含该按钮的显示列表节点。 */
	public Object getTarget() { return this.target; }
	
	/** 创建一个作为参数传递给事件侦听器的 Event 对象。
	 * @param type 事件的类型，可以作为 Event.type 访问。 */
	public Event(String type)
	{
		this.type = type;
	}
	
	/** 检查是否已对事件调用 preventDefault() 方法。<br>
	 * 如果已调用 preventDefault() 方法，则返回 true；否则返回 false。 */
	public boolean isDefaultPrevented()
	{
		return false;
	}
	
	/** 如果可以取消事件的默认行为，则取消该行为。 <br> <br>
	 * 许多事件都有默认执行的关联行为。 <br>
	 * 例如，如果用户在文本字段中键入一个字符，则默认行为就是在文本字段中显示该字符。 <br>
	 * 由于可以取消 TextEvent.TEXT_INPUT 事件的默认行为，因此您可以使用 preventDefault() 方法来防止显示该字符。  <br>
	 * 不可取消行为的一个示例是与 Event.REMOVED 事件关联的默认行为，只要 Flash Player 从显示列表中删除显示对象，就会生成该事件。 <br> <br>
	 * 由于无法取消默认行为（删除元素），因此 preventDefault() 方法对此默认行为无效。  <br>
	 * 您可以使用 Event.cancelable 属性来检查是否可以防止与特定事件关联的默认行为。 <br>
	 * 如果 Event.cancelable 的值为 true，则可以使用 preventDefault() 来取消事件；否则，preventDefault() 无效。*/
	public void preventDefault()
	{
		//未实现
	}
	
	/** 防止对事件流中当前节点的后续节点中的所有事件侦听器进行处理。<br><br>
	 * 此方法不会影响当前节点 (currentTarget) 中的任何事件侦听器。<br>
	 * 相比之下，stopImmediatePropagation() 方法可以防止对当前节点中和后续节点中的事件侦听器进行处理。<br>
	 * 对此方法的其他调用没有任何效果。可以在事件流的任何阶段中调用此方法。 <br>
	 * 注意：此方法不会取消与此事件相关联的行为；有关此功能的信息，请参阅 preventDefault()。 */
	public void stopPropagation()
	{
		//未实现
	}
	
	/** 防止对事件流中当前节点中和所有后续节点中的事件侦听器进行处理。<br><br>
	 * 此方法会立即生效，并且会影响当前节点中的事件侦听器。<br>
	 * 相比之下，在当前节点中的所有事件侦听器都完成处理之前，stopPropagation() 方法不会生效。 <br>
	 * 注意：此方法不会取消与此事件相关联的行为；有关此功能的信息，请参阅 preventDefault()。 */
	public void stopImmediatePropagation()
	{
		//未实现
	}
	
}