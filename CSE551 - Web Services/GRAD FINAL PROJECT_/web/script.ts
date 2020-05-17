import '@babel/polyfill'
import $ from 'jquery'
import _ from 'lodash'

import {
  parsePhoneNumberFromString,
  PhoneNumber,
  E164Number,
} from 'libphonenumber-js'

import trashIcon from 'bootstrap-icons/icons/trash.svg'

import {
  sendAjaxRequest,
} from './ajaxLib'

const apiBaseUri = 'https://gcctp5xjeb.execute-api.us-east-1.amazonaws.com/default'

type Name = string
type WishList = string
type StatusMessage = string;

interface RequestParams {
  participants: Array<[Name, E164Number, WishList]>;
}

interface ResponseInfo {
  message: StatusMessage;
  list: Array<string>;
}

async function initiateSecretSantaExchange(params: RequestParams): Promise<StatusMessage> {
  console.log('Initiating secret santa exchange')
  const response = await sendAjaxRequest({
    method: 'POST',
    url: apiBaseUri,
    contentType: 'application/json',
    data: JSON.stringify(params),
  }) as ResponseInfo

  const {
    message,
    list,
  } = response

  return message
}

let personTally = 0

function addPerson() {
  personTally += 1

  $('#people-list').append(
    $('<div class="form-row">').append(
      $('<div class="col">').append(
        $('<label>')
          .attr('for', `participant-${personTally}-name`)
          .text('Participant name'),
        $('<input>')
          .attr('id', `participant-${personTally}-name`)
          .addClass('form-control')
          .attr('type', 'text')
          .attr('aria-describedby', `participant-${personTally}-name-help`)
          .attr('placeholder', 'John Doe')
          .attr('required', 'true'),
        $('<small>')
          .attr('id', `participant-${personTally}-name-help`)
          .addClass('form-text text-muted')
          .text('Full name of person participating'),
      ),
      $('<div class="col">').append(
        $('<label>')
          .attr('for', `participant-${personTally}-phone`)
          .text('Participant phone number'),
        $('<input>')
          .attr('id', `participant-${personTally}-phone`)
          .addClass('form-control')
          .attr('type', 'tel')
          .attr('aria-describedby', `participant-${personTally}-phone-help`)
          .attr('placeholder', 'XXX-YYY-ZZZZ')
          .attr('required', 'true'),
        $('<small>')
          .attr('id', `participant-${personTally}-phone-help`)
          .addClass('form-text text-muted')
          .text('Phone number of participant'),
      ),
      $('<div class="col">').append(
        $('<label>')
          .attr('for', `participant-${personTally}-wishlist`)
          .text('Participant wish list'),
        $('<input>')
          .attr('id', `participant-${personTally}-wishlist`)
          .addClass('form-control')
          .attr('type', 'tel')
          .attr('aria-describedby', `participant-${personTally}-wishlist-help`)
          .attr('placeholder', 'Board games, etc.')
          .attr('required', 'true'),
        $('<small>')
          .attr('id', `participant-${personTally}-wishlist-help`)
          .addClass('form-text text-muted')
          .text('Wish list of participant'),
      ),
      // $('<div class="col">').append(
      //   $('<label>')
      //     .attr('for', 'participant-${personTally}-delete')
      //     .text('Delete this person'),
      //   $('<button>')
      //     .attr('id', 'participant-${personTally}-delete')
      //     .addClass('btn')
      //     .addClass('btn-danger')
      //     .addClass('form-control')
      //     .attr('type', 'button')
      //     .append(
      //       $('<img>')
      //         .attr('src', trashIcon),
      //     )
      // ),
    )
  )
}

function handleSubmit(event: Event) {
  event.preventDefault()

  const formElement = document.querySelector('#form') as HTMLFormElement
  console.dir(formElement.checkValidity())

  // Get form data
  const formData = $('#people-list .form-row')
    .map((index, elem) => ({
      name: {
        elemId: $('div.col:first-child input', elem)
          .attr('id') as string,
        value: $('div.col:first-child input', elem)
          .val() as string,
      },
      phone: {
        elemId: $('div.col:nth-child(2) input', elem)
          .attr('id') as string,
        value: parsePhoneNumberFromString(
            $('div.col:nth-child(2) input', elem)
              .val() as string,
            'US',
        ),
      },
      wishList: {
        elemId: $('div.col:nth-child(3) input', elem)
          .attr('id') as string,
        value: $('div.col:nth-child(3) input', elem)
          .val() as string,
      },
    }))
    .get()

  // Validate form data
  formData.forEach((datum) => {
    const {
      phone: {
        elemId: phoneElemId,
        value: phone,
      },
    } = datum

    if (phone === undefined) {
      $(`#${phoneElemId}`)
        .addClass('is-invalid')
    }
  })
  if (formData.some(({ phone }) => phone === undefined)) {
    return false
  }

  const validFormData = formData.map(({
    name: { value: name },
    phone: { value: phone },
    wishList: { value: wishList },
  }) => ({
    name,
    phone: phone as PhoneNumber,
    wishList,
  }))

  // Data is valid
  $('#people-list .is-invalid')
    .removeClass('is-invalid')
  $('#form')
    .addClass('was-validated')

  // Submit data
  initiateSecretSantaExchange({
    participants: validFormData.map(({ name, phone, wishList }) => ([name, phone.number.substr(2), wishList])),
  })
}

$(document).ready(() => {
  $('#form').submit((event) => handleSubmit(event as unknown as Event))

  $('#add-person-button').click(() => addPerson())
  addPerson()
})
