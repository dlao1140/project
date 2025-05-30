import { ChangeEvent,Dispatch,KeyboardEvent,SetStateAction,forwardRef } from 'react';
import './style.css';
import { on } from 'events';


//            interface: Input Box 컴포넌트 propperties     //
interface Props{
    label: string;
    type: 'text' | 'password';
    placeholder: string;
    value: string;
    onChange: (event: ChangeEvent<HTMLInputElement>) => void;
    error: boolean;

    icon?: 'eye-light-off-icon' | 'eye-light-on-icon'| 'expand-right-light-icon';
    onButtonClick?: () => void;

    message?: string;

    onKeyDown?: (event: KeyboardEvent<HTMLInputElement>) => void;
}
//      component: Input Box 컴포넌트      //
const InputBox = forwardRef<HTMLInputElement, Props>((props: Props, ref) => {

//      state: properties      //
const { label, type, error, placeholder, value,icon, message } = props;
const { onChange,onButtonClick, onKeyDown } = props;


//      event handler: Input 키 이벤트 처리함수      //
const onKeyDownHandelr = (event: KeyboardEvent<HTMLInputElement>) => {
    if(!onKeyDown) return;
    onKeyDown(event);
}
//      render: Input Box 컴포넌트       //
    return(
        <div className="input-box">
            <div className="inputbox-label">{label}</div>
            <div className={error ? 'inputbox-container-error' : 'inputbox-container'}>
                <input ref={ref} type={type} className = 'input' placeholder ={placeholder} value={value} onChange={onChange} onKeyDown={onKeyDownHandelr}/>
                {onButtonClick !== undefined && 
                <div className ='icon-button' onClick={onButtonClick}>
                    {icon !== undefined && <div className ={`icon ${icon}`}></div>}
                </div>
                }
            </div>
            {message !== undefined && <div className="inputbox-message">{message}</div>}
        </div>
    )
});

export default InputBox;