// src/components/Modal/index.tsx
import React, { FC, ReactNode, useRef, useState } from 'react'
import ReactDOM from 'react-dom'
import { CSSTransition } from 'react-transition-group'
import './style.css'

interface ModalProps {
  show: boolean
  title: string
  children: ReactNode
  onClose: () => void
  customFooter?: ReactNode
}

const Modal: FC<ModalProps> = ({ show, title, children, onClose, customFooter }) => {
  const backdropRef = useRef<HTMLDivElement>(null)
  const modalRef = useRef<HTMLDivElement>(null)

  // 모달이 완전히 등장한 후에만 백드롭 클릭으로 닫히도록 허용
  const [allowBackdropClick, setAllowBackdropClick] = useState(false)

  return ReactDOM.createPortal(
    <>
      <CSSTransition
        nodeRef={backdropRef}
        in={show}
        timeout={{ enter: 400, exit: 500 }}
        mountOnEnter
        unmountOnExit
        classNames="fade"
      >
        <div
          className="modal-backdrop"
          ref={backdropRef}
          // 백드롭 클릭 닫기 로직을 완전히 제거합니다
        />
      </CSSTransition>

      <CSSTransition
        nodeRef={modalRef}
        in={show}
        timeout={{ enter: 400, exit: 500 }}
        mountOnEnter
        unmountOnExit
        classNames="slide"
        onEntered={() => setAllowBackdropClick(true)}   // 모달 완전 등장 시
        onExited={() => setAllowBackdropClick(false)}   // 모달 완전 퇴장 시
      >
        <div
          className="modal-content"
          ref={modalRef}
          onClick={e => e.stopPropagation()}  // 내부 클릭으로 절대 닫히지 않도록
        >
          <div className="modal-header">
            <h2 className="modal-title">{title}</h2>
            <button className="modal-close-button" onClick={onClose}>×</button>
          </div>

          <div className="modal-body">
            {children}
          </div>

          {customFooter && (
            <div className="modal-footer">
              <div className="modal-footer-buttons">
                {customFooter}
              </div>
            </div>
          )}
        </div>
      </CSSTransition>
    </>,
    document.getElementById('modal-root')!
  )
}

export default Modal
